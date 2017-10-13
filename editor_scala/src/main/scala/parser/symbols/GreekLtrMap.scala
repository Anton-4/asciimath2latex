package parser.symbols

import parser.symbols.OpMap.opMap


object GreekLtrMap extends SymbolMap{

  val greekMap = Map(
    "alpha"->"\\alpha",
    "beta"->"\\beta",
    "chi"->"\\chi",
    "delta"->"\\delta",
    "Delta"->"\\Delta",
    "epsi"->"\\epsilon",
    "varepsilon"->"\\varepsilon",
    "eta"->"\\eta",
    "gamma"->"\\gamma",
    "Gamma"->"\\Gamma",
    "iota"->"\\iota",
    "kappa"->"\\kappa",
    "lambda"->"\\lambda",
    "Lambda"->"\\Lambda",
    "lamda"->"\\lamda",
    "Lamda"->"\\Lamda",
    "mu"->"\\mu",
    "nu"->"\\nu",
    "omega"->"\\omega",
    "Omega"->"\\Omega",
    "phi"->"\\phi",
    "varphi"->"\\varphi",
    "Phi"->"\\Phi",
    "pi"->"\\pi",
    "Pi"->"\\Pi",
    "psi"->"\\psi",
    "Psi"->"\\Psi",
    "rho"->"\\rho",
    "sigma"->"\\sigma",
    "Sigma"->"\\Sigma",
    "tau"->"\\tau",
    "theta"->"\\theta",
    "vartheta"->"\\vartheta",
    "Theta"->"\\Theta",
    "upsilon"->"\\upsilon",
    "xi"->"\\xi",
    "Xi"->"\\Xi",
    "zeta"->"\\zeta"
  )

  override def getMap(): Map[String, String] = greekMap

}
