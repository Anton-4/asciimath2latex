package parser.symbols

object OpMap extends SymbolMap{

  val opMap = Map(
    "+" -> "+",
    "-" -> "-",
    "=" -> "=",
    "&=" -> "&=",
    "*" -> "\\cdot ",
    "**"->"\\ast",
    "***"->"\\star",
    "//"->"\\//",
    "\\\\"->"\\backslash",
    "setminus"->"\\setminus",
    "xx"->"\\times",
    "|><"->"\\ltimes",
    "><|"->"\\rtimes",
    "|><|"->"\\bowtie",
    "-:"->"\\div ",
    "divide"->"\\divide",
    "@"->"\\circ",
    "o+"->"\\oplus",
    "ox"->"\\otimes",
    "o."->"\\odot",
    "sum"->"\\sum",
    "prod"->"\\prod",
    "^^"->"\\wedge",
    "^^^"->"\\bigwedge",
    "vv"->"\\vee",
    "vvv"->"\\bigvee",
    "nn"->"\\cap",
    "nnn"->"\\bigcap",
    "uu"->"\\cup",
    "uuu"->"\\bigcup"
  )

  override def getMap(): Map[String, String] = opMap

}
