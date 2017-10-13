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


  val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz,:"
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

  val all: Parser[String] = P( text | align | fraction | braceBlock | symbl)
  val symbl = P( operator | greekLtr | binRelation | logical | misc | func | arrows |
    sub | sup | number | whitespace | variable | newline)
  val sub: Parser[String] = P("_" ~ all).map(x => "_{" + x.mkString("") + "}")
  val sup: Parser[String] = P("^" ~ all).map(x => "^{" + x.mkString("") + "}")

  val curlyBlock = P("{" ~ all.rep() ~ "}").map(x => "{" + x.mkString("") + "}")
  val litCurlyBlock = P("\\{" ~ all.rep() ~ "\\}").map(x => "\\{" + x.mkString("") + "\\}")
  val roundBlock = P("(" ~ all.rep() ~ ")").map(x => "\\left(" + x.mkString("") + "\\right)")
  val roundToCurlyBlock = P("(" ~ all.rep() ~ ")").map(x => "{" + x.mkString("") + "}")
  val squareBlock = P("[" ~ all.rep() ~ "]").map(x => "\\left[" + x.mkString("") + "\\right]")
  val braceBlock = curlyBlock | litCurlyBlock | roundBlock | squareBlock

  val bracedFraction = P(roundToCurlyBlock ~ "/" ~ roundToCurlyBlock).map({case (nume, divi) => s"\\frac$nume$divi"} )
  val mixedFractionA = P(roundToCurlyBlock ~ "/" ~ symbl).map({case (nume, divi) => s"\\frac$nume{$divi}"} )
  val mixedFractionB = P(symbl ~ "/" ~ roundToCurlyBlock).map({case (nume, divi) => s"\\frac{$nume}$divi"} )
  val simpleFraction = P(symbl ~ "/" ~ symbl).map({case (nume, divi) => s"\\frac{$nume}{$divi}"} )
  val fraction = bracedFraction | mixedFractionA | mixedFractionB | simpleFraction

  val text = P("@" ~ CharsWhile(c => c != '@').! ~ "@").map(x => "\\text{" + x + "}")


  val align = P("eq" ~ all.rep() ~ "endeq").map({case (x) => "\\begin{align} " + x.mkString("") + "\\end{align} "})

  val equation = all.rep.map(_.mkString(""))

  def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = AsciiMathParser.equation.parse(input)
    value
  }

}
