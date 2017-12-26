package parser.symbols

object HeaderMap extends SymbolMap {

  val headerMap = Map(
    "###" -> "\\subsubsection*",
    "##" -> "\\subsection*",
    "#" -> "\\section*"
  )

  override def getMap(): Map[String, String] = headerMap

}

