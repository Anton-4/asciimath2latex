import fastparse.core.Parsed

import collection.mutable.Stack
import org.scalatest._
import parser.AsciiMathParser
import parser.TempParser
import parser.AsciiMathTree

class ParserSpec extends FlatSpec with Matchers {

  /*"parser" should "parse greek letters correctly" in {
    val input = "omega lambda alpha\n beta Omega"
    AsciiMathParser(input).toLatex() shouldBe ("\\omega \\lambda \\alpha \\\\\n\\beta \\Omega")
  }*/

  "parser" should "parse symbols without spaces correctly" in {
    val Parsed.Success(value, successIndex) = TempParser.equation.parse("+oo")

    value shouldBe "+\\infty"
  }

  /*"parser" should "parse sums correctly" in {
    AsciiMathParser("sum").toLatex() shouldBe ("\\sum")
    AsciiMathParser("sum_alpha").toLatex() shouldBe ("\\sum_{\\alpha}")
    AsciiMathParser("sum_alpha^beta").toLatex() shouldBe ("\\sum_{\\alpha}^{\\beta}")
    AsciiMathParser("sum^beta_alpha").toLatex() shouldBe ("\\sum_{\\alpha}^{\\beta}")
  }*/
}
