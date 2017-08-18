package parser

import scala.util.parsing.combinator.RegexParsers

object AsciiMathParser extends RegexParsers with AsciiMathTree{
  

  def token: Parser[Token] = {
    val variable = "([A-Za-z]+)".r
    val num = "(\\d+(\\.\\d+)?)".r

    "[^\\s_^]+".r ^^{
      case  "alpha" => Alpha
      case  "beta" => Beta
      case  "chi" => Chi
      case  "delta" => Delta
      case  "Delta" => BigDelta
      case  "epsi" => Epsi
      case  "varepsilon" => Varepsilon
      case  "eta" => Eta
      case  "gamma" => Gamma
      case  "Gamma" => BigGamma
      case  "iota" => Iota
      case  "kappa" => Kappa
      case  "lambda" => Lambda
      case  "Lambda" => BigLambda
      case  "mu" => Mu
      case  "nu" => Nu
      case  "omega" => Omega
      case  "Omega" => BigOmega
      case  "phi" => Phi
      case  "varphi" => Varphi
      case  "Phi" => BigPhi
      case  "pi" => Pi
      case  "Pi" => BigPi
      case  "psi" => Psi
      case  "Psi" => BigPsi
      case  "rho" => Rho
      case  "sigma" => Sigma
      case  "Sigma" => BigSigma
      case  "tau" => Tau
      case  "theta" => Theta
      case  "vartheta" => Vartheta
      case  "Theta" => BigTheta
      case  "upsilon" => Upsilon
      case  "xi" => Xi
      case  "Xi" => BigXi
      case  "zeta" => Zeta
      case "sum" => Sum()
      case "prod" => Prod()
      case "^^" => Wedge
      case "^^^" => BigWedge()
      case "vv" => Vee
      case "vvv" => BigVee()
      case "nn" => Cap
      case "nnn" => BigCap()
      case "uu" => Cup
      case "uuu" => BigCap()
      case "!=" => NotEq
      case ":=" => Define
      case "lt" => Low
      case "<=" => LowEq
      case "gt" => Great
      case ">=" => GreatEq
      case "-<" => Prec
      case ">-" => Succ
      case "-<=" => PrecEq
      case ">-=" => SuccEq
      case "in" => In
      case "!in" => NotIn
      case "sub" => Subset
      case "sup" => Supset
      case "-=" => Equiv
      case "~=" => Cong
      case "~~" => Approx
      case "prop" => Propto
      case "and" => And
      case "or" => Or
      case "not" => Not
      case "=>" => Implies
      case "if" => If
      case "<=>" => Iff
      case "AA" => Forall
      case "EE" => Exists
      case "_|_" => Bot
      case "TT" => Top
      case "|--" => Vdash
      case "|==" => Models
      case "(:" => LAngle
      case ":)" => RAngle
      case "int" => Integral()
      case "dx" => DerX
      case "dy" => DerY
      case "dz" => DerZ
      case "dt" => DerT
      case "oint" => OIntegral()
      case "del" => Partial
      case "grad" => Nabla
      case "+-" => PlusMin
      case "O/" => EmptySet
      case "oo" => Infty
      case "aleph" => Aleph
      case "..." => LDots
      case ":." => Therefore
      case ":'" => Because
      case "/_" => Angle
      case "/_\\" => Triangle
      case "'" => Prime
      case "tilde" => Tilde
      case "frown" => Frown
      case "quad" => Quad
      case "qquad" => QQuad
      case "cdots" => CDots
      case "vdots" => VDots
      case "ddots" => DDots
      case "diamond" => Diamond
      case "square" => Square
      case "|__" => LFloor
      case "__|" => RFloor
      case "|~" => LCeiling
      case "~|" => Rceiling
      case "CC" => Complex()
      case "NN" => Natural()
      case "QQ" => Rational()
      case "RR" => Real()
      case "ZZ" => IntColl()
      case variable(v) => Variable(v)
      case num(n) => Number(n)
      case miscStr => Misc(miscStr)
    }
  }

  def operator : Parser[Operator] = {
    """[TODO]+""".r ^^ {
      case "=" => Equals
      case "+" => Plus
      case "-" => Minus
      case "*" => Times
      case "**" => Asterix
      case "***" => Star
      case "/" => Slash
      case "\\\\" => Backslash
      case "setminus" => SetMinus
      case "xx" => XTimes
      case "|><" => LTimes
      case "><|" => RTimes
      case "|><|" => Bowtie
      case "-:" => Div
      case "@" => At
      case "o+" => OPlus
      case "ox" => OTimes
      case "o." => ODot
    }
  }

  def eol: Parser[Any] = sys.props("line.separator")
  def expression: Parser[Expression] = (underOverToken).+ ^^ {case ltrs => Expression(ltrs)}
  def line: Parser[Expression] = expression.+ ~ eol.* ^^ {case expr~_ => Expression(expr)}
  def document: Parser[Document] = line.+ ^^ {case lines => Document(lines)}

  def under: Parser[UnderOver] = "_" ~ expression ^^ {case _~a => UnderOver(under = Some(a))}
  def over: Parser[UnderOver] = "^" ~ expression ^^ {case _~a => UnderOver(over = Some(a))}

  def overOrUnder: Parser[UnderOver] = (under | over) ^^ {
    case a => a
  }
  def underOverToken: Parser[Token] = token ~ (overOrUnder?) ~ (overOrUnder?) ^^ {
    case (tok:Token) ~ None ~ None => tok
    case (tok:HasUnderOver) ~ Some(ovrUndr) ~ None => {
      tok.underOver = ovrUndr
      tok
    }
    case (tok:HasUnderOver) ~ Some(ovrUndrA) ~ Some(ovrUndrB) => {
      tok.underOver = ovrUndrA.combine(ovrUndrB)
      tok
    }
  }

  override val whiteSpace = [ \t]+""".r

  def apply(input: String): Document = parseAll(document, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }

}
