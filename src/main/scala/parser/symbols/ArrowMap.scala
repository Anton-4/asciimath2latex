package parser.symbols

object ArrowMap extends SymbolMap {

  val arrowMap = Map(
    "uarr" -> "\\uparrow",
    "darr" -> "\\downarrow",
    "rarr" -> "\\rightarrow",
    "->" -> "\\to",
    ">->" -> "\\rightarrowtail",
    "->>" -> "\\twoheadrightarrow",
    ">->>" -> "\\twoheadrightarrowtail",
    "|->" -> "\\mapsto",
    "larr" -> "\\leftarrow",
    "harr" -> "\\leftrightarrow",
    "rArr" -> "\\Rightarrow",
    "lArr" -> "\\Leftarrow",
    "hArr" -> "\\Leftrightarrow"
  )

  override def getMap(): Map[String, String] = arrowMap

}
