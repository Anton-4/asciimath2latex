package parser.symbols

import parser.symbols.OpMap.opMap

object RelMap extends SymbolMap{

  val relMap = Map(
     "!=" -> "\\neq ",
     ":=" -> ":= ",
     "<" -> "< ",
     "lt" -> "\\lt ",
     "<=" -> "\\le ",
     "gt" -> "\\gt ",
     ">" -> "> ",
     ">=" -> "\\ge ",
     "-<" -> "\\prec ",
     ">-" -> "\\succ ",
     "-<=" -> "\\preceq ",
     ">-=" -> "\\succeq ",
     "in" -> "\\in ",
     "!in" -> "\\notin ",
     "sub" -> "\\subset ",
     "sup" -> "\\supset ",
     "-=" -> "\\equiv ",
     "~=" -> "\\cong ",
     "~~" -> "\\approx ",
     "~" -> "\\sim ",
     "prop" -> "\\propto "
  )

  override def getMap(): Map[String, String] = relMap

}
