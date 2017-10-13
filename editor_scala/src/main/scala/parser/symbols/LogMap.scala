package parser.symbols

import parser.symbols.RelMap.relMap

object LogMap extends SymbolMap{
  
  val logMap = Map(
  "and" -> "\\and",
  "or" -> "\\pr",
  "not" -> "\\neg",
  "->" -> "\\implies",
  "if" -> "\\if",
  "<->" -> "\\iff",
  "AA" -> "\\forall",
  "EE" -> "\\exists",
  "_|_" -> "\\bot",
  "TT" -> "\\top",
  "|--" -> "\\vdash",
  "|==" -> "\\models"
  )

  override def getMap(): Map[String, String] = logMap

}
