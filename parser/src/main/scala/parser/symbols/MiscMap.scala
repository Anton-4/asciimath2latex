package parser.symbols

object MiscMap extends SymbolMap {

  val miscMap = Map(
  "int" -> "\\int ",
  "oint" -> "\\oint ",
  "del" -> "\\partial ",
  "grad" -> "\\nabla ",
  "O/" -> "\\emptyset ",
  "oo" -> "\\infty ",
  "aleph" -> "\\aleph ",
  "..." -> "\\ldots ",
  "?" -> "?",
  "!" -> "!",
  "&" -> "&",
  ":." -> "\\therefore ",
  ":'" -> "\\because ",
  "/_" -> "\\angle ",
  "/_\\" -> "\\triangle ",
  "'" -> "\\sp{\\prime} ",
  "tilde" -> "\\tilde ",
  "frown" -> "\\frown ",
  "quad" -> "\\quad ",
  "qquad" -> "\\qquad ",
  "cdots" -> "\\cdots ",
  "vdots" -> "\\vdots ",
  "ddots" -> "\\ddots ",
  "diamond" -> "\\diamond ",
  "square" -> "\\square ",
  "|__" -> "\\lfloor ",
  "__|" -> "\\rfloor ",
  "|~" -> "\\lceiling ",
  "~|" -> "\\rceiling ",
  "||" -> "\\bigg\\rvert ",
  "CC" -> "\\mathbb{C}",
  "NN" -> "\\mathbb{N}",
  "QQ" -> "\\mathbb{Q}",
  "RR" -> "\\mathbb{R}",
  "ZZ" -> "\\mathbb{Z}"
  )

  override def getMap(): Map[String, String] = miscMap

}
