package parser.symbols

object FunMap extends SymbolMap {

  val funMap = Map(
    "lim" -> "\\lim",
    "Lim" -> "\\Lim",
    "sin" -> "\\sin",
    "cos" -> "\\cos",
    "tan" -> "\\tan",
    "sinh" -> "\\sinh",
    "cosh" -> "\\cosh",
    "tanh" -> "\\tanh",
    "cot" -> "\\cot",
    "sec" -> "\\sec",
    "csc" -> "\\csc",
    "arcsin" -> "\\arcsin",
    "arccos" -> "\\arccos",
    "arctan" -> "\\arctan",
    "coth" -> "\\coth",
    "sech" -> "\\sech",
    "csch" -> "\\csch",
    "exp" -> "\\exp",
    "abs" -> "\\abs",
    "norm" -> "\\norm",
    "floor" -> "\\floor",
    "ceil" -> "\\ceil",
    "log" -> "\\log",
    "ln" -> "\\ln",
    "det" -> "\\det",
    "dim" -> "\\dim",
    "mod" -> "\\mod",
    "gcd" -> "\\gcd",
    "lcm" -> "\\lcm",
    "lub" -> "\\lub",
    "glb" -> "\\glb",
    "min" -> "\\min",
    "max" -> "\\max"
  )

  override def getMap(): Map[String, String] = funMap

}
