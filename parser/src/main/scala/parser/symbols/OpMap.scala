package parser.symbols

object OpMap extends SymbolMap{

  val opMap = Map(
    "+-" -> "\\pm ",
    "+" -> "+",
    "-" -> "-",
    "=" -> "=",
    "&=" -> "&=",
    "\\*" -> "*",
    "***"->"\\star ",
    "**"->"\\ast ",
    "*" -> "\\cdot ",
    "//"->"\\//",
    "\\\\"->"\\backslash ",
    "setminus"->"\\setminus ",
    "xx"->"\\times ",
    "|><"->"\\ltimes ",
    "><|"->"\\rtimes ",
    "|><|"->"\\bowtie ",
    "-:"->"\\div ",
    "divide"->"\\divide ",
    "@"->"\\circ ",
    "o+"->"\\oplus ",
    "ox"->"\\otimes ",
    "o."->"\\odot ",
    "sum"->"\\sum ",
    "sqrt"->"\\sqrt ",
    "prod"->"\\prod ",
    "^^"->"\\wedge ",
    "^^^"->"\\bigwedge ",
    "vv"->"\\vee ",
    "vvv"->"\\bigvee ",
    "nn"->"\\cap ",
    "nnn"->"\\bigcap ",
    "uu"->"\\cup ",
    "uuu"->"\\bigcup "
  )

  override def getMap(): Map[String, String] = opMap

}
