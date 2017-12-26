package main

import parser.AsciiMathParser
import java.io._

import scala.io.Source


object Main extends App {

  def main() = {
    val asciiMath = Source.fromResource("input.fastm").getLines().toList.mkString("\n")

    val latex = AsciiMathParser.parse(asciiMath)

    val pw = new PrintWriter(new File("output.tex" ))
    pw.write(latex)
    pw.close()
  }

  main()
}

