package parser

import fastparse.all._
import fastparse.core.Parsed
import parser.symbols._

import scala.collection.mutable


object AsciiMathParser{

  private def genSymbolParser(symbolMap: SymbolMap): Parser[String] = {
    val symMap = symbolMap.getMap()
    P(StringIn(symbolMap.keySeq():_*).!).map(symMap(_))
  }

  val greekLtr = genSymbolParser(GreekLtrMap)
  val operator = genSymbolParser(OpMap)
  val binRelation = genSymbolParser(RelMap)
  val logical = genSymbolParser(LogMap)
  val misc = genSymbolParser(MiscMap)
  val func = genSymbolParser(FunMap)
  val arrows = genSymbolParser(ArrowMap)
  val header = genSymbolParser(HeaderMap)

  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,:|"
  val variable: Parser[String] = P(!"endeq" ~ CharsWhileIn(alphabet).!)
  val digits = "0123456789"
  val digit: Parser[String] = P( CharIn(digits).rep.!)
  val decNum: Parser[String] = P( CharsWhileIn(digits).!)
  val exp = P( CharIn("Ee") ~ CharIn("+-").? ~ decNum )
  val ffloat: Parser[String] = {
    def Thing = P( decNum ~ exp.? )
    def Thing2 = P( "." ~ Thing)
    P( ("." ~ Thing | decNum ~ Thing2).! )
  }
  val number = P(ffloat | decNum)

  val whitespace = P(" ").map(_ => "")
  val newline = P( StringIn("\r\n", "\n") ).map(_ => "\\\\\n")

  val all: Parser[String] = P(annotate | fraction | piecewise | braceBlock | symbl)
  val symbl = P( logical | operator | greekLtr | func | binRelation | misc | arrows |
    sub | sup | number | whitespace | variable | newline)
  val sub: Parser[String] = P("_" ~ all).map(x => "_{" + x.mkString("") + "}")
  val sup: Parser[String] = P("^" ~ all).map(x => "^{" + x.mkString("") + "}")

  val curlyBlock = P("{" ~ all.rep() ~ "}").map(x => "{" + x.mkString("") + "}")
  val litCurlyBlock = P("\\{" ~ all.rep() ~ "\\}").map(x => "\\{" + x.mkString("") + "\\}")
  val roundBlock = P("(" ~ all.rep() ~ ")").map(x => "\\left(" + x.mkString("") + "\\right)")
  val roundToCurlyBlock = P("(" ~ all.rep() ~ ")").map(x => "{" + x.mkString("") + "}")
  val squareBlock = P("[" ~ all.rep() ~ "]").map(x => "\\left[" + x.mkString("") + "\\right]")
  val braceBlock = curlyBlock | litCurlyBlock | roundBlock | squareBlock

  val divider = P(whitespace.rep ~ "/" ~ whitespace.rep)
  val bracedFraction = P(roundToCurlyBlock ~ divider ~ roundToCurlyBlock).map({case (nume,_, divi) => s"\\frac$nume$divi"} )
  val mixedFractionA = P(roundToCurlyBlock ~ divider ~ symbl).map({case (nume,_, divi) => s"\\frac$nume{$divi}"} )
  val mixedFractionB = P(symbl ~ divider ~ roundToCurlyBlock).map({case (nume,_, divi) => s"\\frac{$nume}$divi"} )
  val simpleFraction = P(symbl ~ divider ~ symbl).map({case (nume,_, divi) => s"\\frac{$nume}{$divi}"} )
  val fraction = bracedFraction | mixedFractionA | mixedFractionB | simpleFraction

  val text = P(CharsWhile(c => c != '$' && c != '\n').!).map { x =>
    val boldRep = x.replaceAll("\\*\\*([^\\*]*)\\*\\*", "\\\\textbf{$1}")
    val arrowRep = boldRep.replaceAll("->","\\$\\\\rightarrow\\$")
                          .replaceAll("=>","\\$\\\\implies\\$")
                          .replaceAll("%","\\%")
    arrowRep.replaceAll(">", "\\\\textgreater ").replaceAll("<", "\\\\textless ")

  }

  val textToCurly = P(CharsWhile(c => c != '\n').!).map(x => "{" + x + "}")
  val textNewLine = P( StringIn("\r\n", "\n") ).map(_ => "\n")
  val inlineMath = P("$" ~ all.rep() ~ "$").map(x => "$" + x.mkString("") + "$")

  val align = P("$" ~ textNewLine ~ all.rep() ~ "$").map({
    case (_,b) => {
      var firstEq = true
      val withAlign = for (exp <- b) yield {
        if (exp.contains("=")) {

          var retStr = ""
          val bracesOpen = List('{','[')
          val bracesClose = List(']','}')
          val bracesStack = mutable.Stack[Char]()
          for (i <- 0 until exp.length){
            if( bracesOpen.contains(exp(i)) ){
              bracesStack.push(exp(i))
              retStr += exp(i)
            }else if( bracesClose.contains(exp(i)) ){
              bracesStack.pop()
              retStr += exp(i)
            }else if(exp(i) == '=' && bracesStack.isEmpty && firstEq){
                firstEq = false
                retStr += "&="
            }else{
              retStr += exp(i)
            }
          }
          if (exp.contains("\\\\")){
            firstEq = true
          }
          retStr
        } else{
          if (exp.contains("\\\\")){
            firstEq = true
          }
          exp
        }
      }
      val midStr = withAlign.mkString("").dropRight(3) + "\n"
      "\\begin{align*}\n" + midStr + "\\end{align*}"
    }
  })

  val piecewise = P("pw{" ~ textNewLine ~ all.rep() ~ "}").map({
    case (_,b) => "\\begin{cases}\n" + b.mkString("") + "\\end{cases}"
  })
  val annotate = P("@" ~ (inlineMath | text).rep).map(x => "&& \\text{- " + x.mkString("") + " -}")

  val section = P(header ~ text).map({
    case (h, t) => h + "{" + t + " }"
  })

  val tableCell = P("|" ~ (CharsWhile(c => c != '|' && c !='\n') ~ &("|")).!).map(x => x)
  val dashLine = P("|" ~ ("-".rep(min = 1) ~ "|").rep(min = 1) ~ textNewLine).map(_ => "\\hline")
  val tableLine = P(tableCell.rep(min = 1) ~ "|" ~ textNewLine).map(x => x._1.mkString("&") + "\\\\")
  val table = P((dashLine | tableLine).rep(min = 1)).map{
    x => {
      var tabular = "|"
      val nrSeps = x.head.count(_ == '&') + 1
      for (_ <- 0 until nrSeps){
        tabular += "l|"
      }
      "\\begin{table}[H]\n\\centering\n\\begin{tabular}{" + tabular + "}\n" +
      x.mkString("\n") +
      "\n\\end{tabular}\n\\end{table}\n"
    }
  }

  val listItem = P(CharIn(List('-','+')) ~ (text | inlineMath).rep(min = 1)).map(x => "\\item " + x.mkString(""))
  val list = P((listItem ~ textNewLine).rep(min=1)).map{ x =>
    "\\begin{itemize}\n" +
      x.map(tup => tup._1).mkString("\n") +
    "\n\\end{itemize}\n"
  }

  val topAll = P(section | align | list | inlineMath | table | text | textNewLine)
  val equation = topAll.rep.map(_.mkString(""))

  def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = AsciiMathParser.equation.parse(input)
    value
  }

}
