import collection.mutable.Stack
import org.scalatest._
import parser.AsciiMathParser
import parser.AsciiMathTree

class ParserSpec extends FlatSpec with Matchers {

  "parser" should "parse greek letters correctly" in {
    val input = "omega lambda alpha\n beta Omega"
    AsciiMathParser(input).toLatex() shouldBe ("\\omega \\lambda \\alpha \\\\\n\\beta \\Omega")
  }

  "parser" should "parse sums correctly" in {
    val input = "sum"
    AsciiMathParser("sum").toLatex() shouldBe ("\\sum")
    AsciiMathParser("sum_alpha").toLatex() shouldBe ("\\sum_\\alpha")
    AsciiMathParser("sum_alpha^beta").toLatex() shouldBe ("\\sum_\\alpha^\\beta")
    AsciiMathParser("sum^beta_alpha").toLatex() shouldBe ("\\sum_\\alpha^\\beta")
  }
}
