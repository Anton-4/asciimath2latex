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

  private def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = TempParser.equation.parse(input)
    value
  }

  "parser" should "parse numbers correctly" in {
      parse("123") shouldBe "123"
  }

  "parser" should "parse powers correctly" in {
    parse("1^2^3^4^5") shouldBe "1^{2^{3^{4^{5}}}}"
  }

  "parser" should "parse symbols without spaces correctly" in {
    parse("oo+") shouldBe "\\infty+"
  }

  "parser" should "parse simple equations correctly" in {
    parse("2=1+1") shouldBe "2=1+1"
    parse("2 = 1+1") shouldBe "2=1+1"
    parse("2+2-    2 = 1+1") shouldBe "2+2-2=1+1"
    parse("2*2 = 4") shouldBe "2\\cdot2=4"
  }

  "parser" should "parse greek letters correctly" in {
    parse("alpha") shouldBe "\\alpha"
    parse("alpha beta") shouldBe "\\alpha\\beta"
  }

  /*"parser" should "parse sums correctly" in {
    AsciiMathParser("sum").toLatex() shouldBe ("\\sum")
    AsciiMathParser("sum_alpha").toLatex() shouldBe ("\\sum_{\\alpha}")
    AsciiMathParser("sum_alpha^beta").toLatex() shouldBe ("\\sum_{\\alpha}^{\\beta}")
    AsciiMathParser("sum^beta_alpha").toLatex() shouldBe ("\\sum_{\\alpha}^{\\beta}")
  }*/
}
