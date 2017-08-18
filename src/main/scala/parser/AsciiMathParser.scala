package parser

import scala.util.parsing.combinator.RegexParsers

object AsciiMathParser extends RegexParsers with AsciiMathTree{
  

  def token: Parser[Token] = {

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
      case "+" => Plus
      case "-" => Minus
      case "*" => Times
      case "**" => Asterix
      case "***" => Star
      case "//" => Slash
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

    }
  }

  def under: Parser[UnderOver] = "_" ~ token ^^ {case _~a => UnderOver(under = Some(a))}
  def over: Parser[UnderOver] = "^" ~ token ^^ {case _~a => UnderOver(over = Some(a))}

  def overOrUnder: Parser[UnderOver] = (under | over) ^^ {
    case a => a
  }
  def underOverToken: Parser[Token] = token ~ (overOrUnder?) ~ (overOrUnder?) ^^ {
    case (expr:Token) ~ None ~ None => expr
    case (expr:HasUnderOver) ~ Some(ovrUndr) ~ None => {
      expr.underOver = ovrUndr
      expr
    }
    case (expr:HasUnderOver) ~ Some(ovrUndrA) ~ Some(ovrUndrB) => {
      expr.underOver = ovrUndrA.combine(ovrUndrB)
      expr
    }
  }

  override val whiteSpace = """[ \t]+""".r

  def eol: Parser[Any] = sys.props("line.separator")
  def equation: Parser[Equation] = (underOverToken).+ ~ eol.* ^^ {case ltrs~_ => Equation(ltrs)}

  def document: Parser[Document] = equation.+ ^^ {case equations => Document(equations)}

  def apply(input: String): Document = parseAll(document, input) match {
    case Success(result, _) => result
    case failure : NoSuccess => scala.sys.error(failure.msg)
  }

}
