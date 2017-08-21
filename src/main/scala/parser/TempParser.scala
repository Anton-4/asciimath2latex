package parser

import fastparse.all._
import fastparse.core.Parsed
import parser.symbols.{GreekLtrMap, SymbolMap}

import scala.collection.mutable


object TempParser{

  def genSymbolParser(symbolMap: SymbolMap): Parser[String] = {
    val symMap = symbolMap.getMap()
    P(StringIn(symbolMap.keySeq():_*).!).map(symMap(_))
  }



  val lookupMap = mutable.Map("oo" -> "\\infty","+-" -> "\\pm")
  val keySeq = lookupMap.keys.toSeq
  val symbol: Parser[String] = P(StringIn(keySeq:_*).!).map(lookupMap(_))

  val lookupMapOps = mutable.Map("+" -> "+","-" -> "-","=" -> "=", "*" -> "\\cdot")
  val keySeqOps = lookupMapOps.keys.toSeq
  val operator: Parser[String] = P(StringIn(keySeqOps:_*).!).map(lookupMapOps(_))

  val greekMap = GreekLtrMap.greekMap
  val keySeqGreek = greekMap.keys.toSeq
  val greekLtr: Parser[String] = P(StringIn(keySeqGreek:_*).!).map(greekMap(_))

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

  val all: Parser[String] = P(symbol | operator | greekLtr | sub | sup | number | whitespace)
  val sub: Parser[String] = P("_" ~ all.rep()).map(x => "_{" + x.mkString("") + "}")
  val sup: Parser[String] = P("^" ~ all.rep()).map(x => "^{" + x.mkString("") + "}")


  val equation = all.rep.map(_.mkString(""))

  def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = TempParser.equation.parse(input)
    value
  }

}
