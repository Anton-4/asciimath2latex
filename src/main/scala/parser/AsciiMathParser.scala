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


  val alphabet = 'A' to 'z'
  val variable: Parser[String] = P(CharsWhileIn(alphabet).!)
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

  val all: Parser[String] = P( text | fraction | braceBlock | symbl)
  val symbl = P( operator | greekLtr | binRelation | logical | misc | func | arrows |
    sub | sup | number | whitespace | variable)
  val sub: Parser[String] = P("_" ~ all.rep()).map(x => "_" + x.mkString(""))
  val sup: Parser[String] = P("^" ~ all.rep()).map(x => "^" + x.mkString(""))

  val curlyBlock = P("{" ~ all.rep() ~ "}").map(x => "{" + x.mkString("") + "}")
  val roundBlock = P("(" ~ all.rep() ~ ")").map(x => "(" + x.mkString("") + ")")
  val squareBlock = P("[" ~ all.rep() ~ "]").map(x => "[" + x.mkString("") + "]")
  val braceBlock = curlyBlock | roundBlock | squareBlock


  val bracedFraction = P(curlyBlock ~ "/" ~ curlyBlock).map({case (nume, divi) => s"\\frac$nume$divi"} )
  val mixedFractionA = P(curlyBlock ~ "/" ~ symbl).map({case (nume, divi) => s"\\frac$nume{$divi}"} )
  val mixedFractionB = P(symbl ~ "/" ~ curlyBlock).map({case (nume, divi) => s"\\frac{$nume}$divi"} )
  val simpleFraction = P(symbl ~ "/" ~ symbl).map({case (nume, divi) => s"\\frac{$nume}{$divi}"} )
  val fraction = bracedFraction | mixedFractionA | mixedFractionB | simpleFraction

  val text = P("@" ~ CharsWhile(c => c != '@').! ~ "@").map(x => "\\text{" + x + "}")

  val equation = all.rep.map(_.mkString(""))

  def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = AsciiMathParser.equation.parse(input)
    value
  }

}
