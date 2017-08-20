package parser

import fastparse.all._

import scala.collection.mutable


object TempParser{

  val lookupMap = mutable.Map("oo" -> "\\infty","+-" -> "\\pm")
  val keySeq = lookupMap.keys.toSeq
  val symbol = P(StringIn(keySeq:_*).!).map(lookupMap(_))

  val lookupMapOps = mutable.Map("+" -> "+","-" -> "-")
  val keySeqOps = lookupMapOps.keys.toSeq
  val operator = P(StringIn(keySeqOps:_*).!).map(lookupMapOps(_))

  val equation = P(( symbol | operator).rep).map(_.mkString(""))

}
