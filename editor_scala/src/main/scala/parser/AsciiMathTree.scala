package parser

trait AsciiMathTree {

  abstract class Latexable{
    def toLatex(): String
  }

  abstract class Token extends Latexable{
    def toLatex(): String
  }

  case class Expression(parts: List[Latexable]) extends Latexable{
    def toLatex(): String = {
      parts.map(part => part.toLatex()).mkString(" ")
    }
  }

  /*case class Expression(tokens:){
    def toLatex(): String = {
      tokens.map(tok => tok.toLatex()).mkString(" ")
    }
  }*/

  case class Document(equations: List[Expression]){
    def toLatex(): String = {
      equations.map(equation => equation.toLatex()).mkString(" \\\\\n")
    }
  }

  abstract class GreekLtr extends Token{
    override def toLatex() : String = {
      val Big = "^Big([a-zA-Z]+).*".r

      val latex = this.getClass.getSimpleName match {
        case Big(className) => "\\" + className
        case className => "\\" + className.toLowerCase
      }

      latex.replace("$","")
    }
  }

  case object Alpha extends GreekLtr
  case object Beta extends GreekLtr
  case object Chi extends GreekLtr
  case object Delta extends GreekLtr
  case object BigDelta extends GreekLtr
  case object Epsi extends GreekLtr
  case object Varepsilon extends GreekLtr
  case object Eta extends GreekLtr
  case object Gamma extends GreekLtr
  case object BigGamma extends GreekLtr
  case object Iota extends GreekLtr
  case object Kappa extends GreekLtr
  case object Lambda extends GreekLtr
  case object BigLambda extends GreekLtr
  case object Mu extends GreekLtr
  case object Nu extends GreekLtr
  case object Omega extends GreekLtr
  case object BigOmega extends GreekLtr
  case object Phi extends GreekLtr
  case object Varphi extends GreekLtr
  case object BigPhi extends GreekLtr
  case object Pi extends GreekLtr
  case object BigPi extends GreekLtr
  case object Psi extends GreekLtr
  case object BigPsi extends GreekLtr
  case object Rho extends GreekLtr
  case object Sigma extends GreekLtr
  case object BigSigma extends GreekLtr
  case object Tau extends GreekLtr
  case object Theta extends GreekLtr
  case object Vartheta extends GreekLtr
  case object BigTheta extends GreekLtr
  case object Upsilon extends GreekLtr
  case object Xi extends GreekLtr
  case object BigXi extends GreekLtr
  case object Zeta extends GreekLtr

  abstract class Operator extends Token

  abstract class MathOp extends Operator{
    def toLatex(): String
  }

  case class UnderOver(under:Option[Expression] = None, over:Option[Expression] = None) extends MathOp {
    override def toLatex() = {
      "" + optionToString(under, isUnder = true) + optionToString(over, isUnder = false)
    }

    def optionToString(exp: Option[Expression], isUnder: Boolean): String = {
      exp match {
        case Some(tok) => if (isUnder) "_{" + tok.toLatex() + "}" else "^{" + tok.toLatex() + "}"
        case None => ""
      }
    }

    def combine(underOver: UnderOver): UnderOver = {
      val newUnder = List(underOver.under, under).filter(_.isDefined).head
      val newOver = List(underOver.over, over).filter(_.isDefined).head
      UnderOver(newUnder, newOver)
    }
  }

  abstract class HasUnderOver(var underOver: UnderOver) extends MathOp

  case object Plus extends MathOp{
    override def toLatex() = "+"
  }
  case object Equals extends MathOp{
    override def toLatex() = "="
  }
  case object Minus extends MathOp{
    override def toLatex() = "-"
  }
  case object Times extends MathOp{
    override def toLatex() = "\\cdot"
  }
  case object Asterix extends MathOp{
    override def toLatex() = "\\ast"
  }
  case object Star extends MathOp{
    override def toLatex() = "\\star"
  }
  case object Slash extends MathOp{
    override def toLatex() = "/"
  }
  case object Backslash extends MathOp{
    override def toLatex() = "\\\\"
  }
  case object SetMinus extends MathOp{
    override def toLatex() = "\\setminus"
  }
  case object XTimes extends MathOp{
    override def toLatex() = "\\times"
  }
  case object LTimes extends MathOp{
    override def toLatex() = "\\ltimes"
  }
  case object RTimes extends MathOp{
    override def toLatex() = "\\rtimes"
  }
  case object Bowtie extends MathOp{
    override def toLatex() = "\\bowtie"
  }
  case object Div extends MathOp{
    override def toLatex() = "\\div"
  }
  case object At extends MathOp{
    override def toLatex() = "\\circ"
  }
  case object OPlus extends MathOp{
    override def toLatex() = "\\oplus"
  }
  case object OTimes extends MathOp{
    override def toLatex() = "\\otimes"
  }
  case object ODot extends MathOp{
    override def toLatex() = "\\odot"
  }
  case class Sum(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\sum" + underOver.toLatex()
  }
  case class Prod(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\prod" + underOver.toLatex()
  }
  case object Wedge extends MathOp{
    override def toLatex() = "\\wedge"
  }
  case class BigWedge(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\bigwedge" + underOver.toLatex()
  }
  case object Vee extends MathOp{
    override def toLatex() = "\\vee"
  }
  case class BigVee(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\bigvee" + underOver.toLatex()
  }
  case object Cap extends MathOp{
    override def toLatex() = "\\cap"
  }
  case class BigCap(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\bigcap" + underOver.toLatex()
  }
  case object Cup extends MathOp{
    override def toLatex() = "\\cup"
  }
  case class BigCup(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex() = "\\bigcup" + underOver.toLatex()
  }

  //for symbols that are the same in asciimath and latex
  case class Misc(str: String, underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA) {
    override def toLatex(): String = str + underOver.toLatex()
  }

  abstract class RelOp extends Operator

  case object NotEq extends RelOp{
    override def toLatex(): String = "\\ne"
  }
  case object Define extends RelOp{
    override def toLatex(): String = ":="
  }
  case object Low extends RelOp{
    override def toLatex(): String = "\\lt"
  }
  case object LowEq extends RelOp{
    override def toLatex(): String = "\\leq"
  }
  case object Great extends RelOp{
    override def toLatex(): String = "\\gt"
  }
  case object GreatEq extends RelOp{
    override def toLatex(): String = "\\geq"
  }
  case object Prec extends RelOp{
    override def toLatex(): String = "\\prec"
  }
  case object Succ extends RelOp{
    override def toLatex(): String = "\\succ"
  }
  case object PrecEq extends RelOp{
    override def toLatex(): String = "\\preceq"
  }
  case object SuccEq extends RelOp{
    override def toLatex(): String = "\\succeq"
  }
  case object In extends RelOp{
    override def toLatex(): String = "\\in"
  }
  case object NotIn extends RelOp{
    override def toLatex(): String = "\\notin"
  }
  case object Subset extends RelOp{
    override def toLatex(): String = "\\subset"
  }
  case object Supset extends RelOp{
    override def toLatex(): String = "\\supset"
  }
  case object Equiv extends RelOp{
    override def toLatex(): String = "\\equiv"
  }
  case object Cong extends RelOp{
    override def toLatex(): String = "\\cong"
  }
  case object Approx extends RelOp{
    override def toLatex(): String = "\\approx"
  }
  case object Propto extends RelOp{
    override def toLatex(): String = "\\propto"
  }


  abstract class LogOp extends Operator

  case object And extends LogOp{
    override def toLatex(): String = "\\and"
  }
  case object Or extends LogOp{
    override def toLatex(): String = "\\or"
  }
  case object Not extends LogOp{
    override def toLatex(): String = "\\neg"
  }
  case object Implies extends LogOp{
    override def toLatex(): String = "\\implies"
  }
  case object If extends LogOp{
    override def toLatex(): String = "\\if"
  }
  case object Iff extends LogOp{
    override def toLatex(): String = "\\iff"
  }
  case object Forall extends LogOp{
    override def toLatex(): String = "\\forall"
  }
  case object Exists extends LogOp{
    override def toLatex(): String = "\\exists"
  }
  case object Bot extends LogOp{
    override def toLatex(): String = "\\bot"
  }
  case object Top extends LogOp{
    override def toLatex(): String = "\\top"
  }
  case object Vdash extends LogOp{
    override def toLatex(): String = "\\vdash"
  }
  case object Models extends LogOp{
    override def toLatex(): String = "\\models"
  }


  abstract class Bracket extends Token

  case object LAngle extends Bracket{
    override def toLatex(): String = "\\langle"
  }

  case object RAngle extends Bracket{
    override def toLatex(): String = "\\rangle"
  }

  abstract class MiscSymbols extends Token

  case class Variable(str: String, underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = str + underOver.toLatex()
  }
  case class Number(str: String, underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = str + underOver.toLatex()
  }
  case class Integral(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\int" + underOver.toLatex()
  }
  case object DerX extends MiscSymbols{
    override def toLatex(): String = "dx"
  }
  case object DerY extends MiscSymbols{
    override def toLatex(): String = "dy"
  }
  case object DerZ extends MiscSymbols{
    override def toLatex(): String = "dz"
  }
  case object DerT extends MiscSymbols{
    override def toLatex(): String = "dt"
  }
  case class OIntegral(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\oint"
  }
  case object Partial extends MiscSymbols{
    override def toLatex(): String = "\\partial"
  }
  case object Nabla extends MiscSymbols{
    override def toLatex(): String = "\\nabla"
  }
  case object PlusMin extends MiscSymbols{
    override def toLatex(): String = "\\pm"
  }
  case object EmptySet extends MiscSymbols{
    override def toLatex(): String = "\\emptyset"
  }
  case object Infty extends MiscSymbols{
    override def toLatex(): String = "\\infty"
  }
  case object Aleph extends MiscSymbols{
    override def toLatex(): String = "\\aleph"
  }
  case object LDots extends MiscSymbols{
    override def toLatex(): String = "\\ldots"
  }
  case object Therefore extends MiscSymbols{
    override def toLatex(): String = "\\therefore"
  }
  case object Because extends MiscSymbols{
    override def toLatex(): String = "\\because"
  }
  case object Angle extends MiscSymbols{
    override def toLatex(): String = "\\angle"
  }
  case object Triangle extends MiscSymbols{
    override def toLatex(): String = "\\triangle"
  }
  case object Prime extends MiscSymbols{
    override def toLatex(): String = "\\prime"
  }
  case object Tilde extends MiscSymbols{
    override def toLatex(): String = "\\tilde"
  }
  case object Frown extends MiscSymbols{
    override def toLatex(): String = "\\frown"
  }
  case object Quad extends MiscSymbols{
    override def toLatex(): String = "\\quad"
  }
  case object QQuad extends MiscSymbols{
    override def toLatex(): String = "\\qquad"
  }
  case object CDots extends MiscSymbols{
    override def toLatex(): String = "\\cdots"
  }
  case object VDots extends MiscSymbols{
    override def toLatex(): String = "\\vdots"
  }
  case object DDots extends MiscSymbols{
    override def toLatex(): String = "\\ddots"
  }
  case object Diamond extends MiscSymbols{
    override def toLatex(): String = "\\diamond"
  }
  case object Square extends MiscSymbols{
    override def toLatex(): String = "\\square"
  }
  case object LFloor extends MiscSymbols{
    override def toLatex(): String = "\\lfloor"
  }
  case object RFloor extends MiscSymbols{
    override def toLatex(): String = "\\rfloor"
  }
  case object LCeiling extends MiscSymbols{
    override def toLatex(): String = "\\lceiling"
  }
  case object Rceiling extends MiscSymbols{
    override def toLatex(): String = "\\rceiing"
  }
  case class Complex(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\mathbb{C}" + underOver.toLatex()
  }
  case class Natural(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\mathbb{N}" + underOver.toLatex()
  }
  case class Rational(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\mathbb{Q}" + underOver.toLatex()
  }
  case class Real(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\mathbb{R}" + underOver.toLatex()
  }
  case class IntColl(underOverA: UnderOver = UnderOver()) extends HasUnderOver(underOverA){
    override def toLatex(): String = "\\mathbb{Z}" + underOver.toLatex()
  }


  abstract class Func

  abstract class Arrow

  abstract class Monoid

}
