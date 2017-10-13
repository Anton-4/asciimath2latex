package parser.symbols

trait SymbolMap {

  def getMap(): Map[String, String]

  def keySeq() : Seq[String] = {
    getMap().keys.toSeq
  }

}
