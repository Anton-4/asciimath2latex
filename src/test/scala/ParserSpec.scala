import collection.mutable.Stack
import org.scalatest._
import parser.AsciiMathParser
import parser.AsciiMathTree

class ParserSpec extends FlatSpec with Matchers {

  "parser" should "parse greek letters correctly" in {
    val input = "omega lambda alpha\n beta Omega"
    AsciiMathParser(input).toLatex() shouldBe ("\\omega \\lambda \\alpha\n\\beta \\Omega")
  }
}
