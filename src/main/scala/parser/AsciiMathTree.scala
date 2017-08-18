package parser

trait AsciiMathTree {

  abstract class Token{
    def toLatex(): String
  }

  case class Equation(tokens: List[Token]){
    def toLatex(): String = {
      tokens.map(tok => tok.toLatex()).mkString(" ")
    }
  }

  case class Document(equations: List[Equation]){
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

  abstract class MathOp extends Token{
    def toLatex(): String
  }

  case class UnderOver(under:Option[Token] = None, over:Option[Token] = None) extends MathOp {
    override def toLatex() = {
      "" + optionToString(under, isUnder = true) + optionToString(over, isUnder = false)
    }

    def optionToString(token: Option[Token], isUnder: Boolean): String = {
      token match {
        case Some(tok) => if (isUnder) "_" + tok.toLatex() else "^" + tok.toLatex()
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


  abstract class RelOp

  abstract class LogSymbol

  abstract class Bracket

  abstract class Misc

  abstract class Func

  abstract class Arrow

  abstract class Monoid

}
