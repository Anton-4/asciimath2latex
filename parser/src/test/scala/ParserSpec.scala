import fastparse.core.Parsed

import collection.mutable.Stack
import org.scalatest._
import parser.AsciiMathParserOld
import parser.AsciiMathParser
import parser.AsciiMathTree

class ParserSpec extends FlatSpec with Matchers {

  /*"parser" should "parse greek letters correctly" in {
    val input = "omega lambda alpha\n beta Omega"
    AsciiMathParser(input).toLatex() shouldBe ("\\omega \\lambda \\alpha \\\\\n\\beta \\Omega")
  }*/

  private def parse(input: String): String = {
    val Parsed.Success(value, successIndex) = AsciiMathParser.equation.parse(input)
    value
  }

  "parser" should "parse numbers correctly" in {
      parse("123") shouldBe "123"
  }

  "parser" should "parse powers correctly" in {
    parse("1^2^3^4^5") shouldBe "1^{2}^{3}^{4}^{5}"
  }

  "parser" should "parse symbols without spaces correctly" in {
    parse("oo+") shouldBe "\\infty+"
  }

  "parser" should "parse simple equations correctly" in {
    parse("2=1+1") shouldBe "2=1+1"
    parse("2 = 1+1") shouldBe "2=1+1"
    parse("2+2-    2 = 1+1") shouldBe "2+2-2=1+1"
    parse("2*2 = 4") shouldBe "2\\cdot 2=4"
  }

  "parser" should "parse greek letters correctly" in {
    parse("alpha") shouldBe "\\alpha"
    parse("alpha beta") shouldBe "\\alpha\\beta"
  }

  "parser" should "parse sums correctly" in {
    parse("sum") shouldBe "\\sum"
    parse("sum_alpha") shouldBe "\\sum_{\\alpha}"
    parse("sum_alpha^beta") shouldBe "\\sum_{\\alpha}^{\\beta}"
  }

  "parser" should "parse fractions correctly" in {
    parse("1/5") shouldBe "\\frac{1}{5}"
    parse("(1)/(5)") shouldBe "\\frac{1}{5}"
    parse("(1+2)/3") shouldBe "\\frac{1+2}{3}"
    parse("1/(2+3)") shouldBe "\\frac{1}{2+3}"
  }

  "parser" should "parse regular text correctly" in {
    parse("@hello@") shouldBe "\\text{hello}"
  }

  "parser" should "parse variables correctly" in {
    parse("N") shouldBe "N"
  }

  "parser" should "parse equations correctly" in {
    parse("eq5 = 4 + 1\n=3 + 2\nendeq") shouldBe "\\begin{align} 5=4+1\\\\=3+2\\\\\\end{align} "
  }
}
