package gui

import java.awt.image.BufferedImage

import org.scilab.forge.jlatexmath.{TeXConstants, TeXFormula}
import parser.{AsciiMathParser, TempParser}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.embed.swing.SwingFXUtils
import scalafx.event.ActionEvent
import scalafx.scene.{Group, Scene}
import scalafx.scene.control.{Button, _}
import scalafx.scene.image.{ImageView, WritableImage}
import scalafx.scene.layout._
import scalafx.scene.paint.Color._

object Main extends JFXApp {

  val windowWidth = 500
  val windowHeight = 500

  lazy val editorArea = new TextArea {
    prefColumnCount = 40
    prefRowCount = 20
  }

  lazy val latexTextView = new TextArea {
    editable = false
    prefColumnCount = 40
    prefRowCount = 20
  }

  val btnToLatex = new Button {
    text = "to_latex"
  }


  val imageView = new ImageView()
  imageView.image = genTexImg("LateX")

  btnToLatex.onAction = (event: ActionEvent) => {
    val latex = TempParser.parse(editorArea.getText)
    imageView.image = genTexImg(latex)
    latexTextView.text = latex
  }

  val rootPane = new Group
  rootPane.children = List(editorArea)


  val flowPane = new FlowPane {
    children = List(editorArea, imageView, latexTextView, btnToLatex)
  }

  stage = new PrimaryStage {
    title = "asciimath_editor"
    scene = new Scene() {
      stylesheets += getClass.getResource("styles.css").toExternalForm
      fill = rgb(230, 230, 230)
      content = flowPane
    }
  }


  private def genTexImg(texText: String): WritableImage = {
    val tex = new TeXFormula(texText)
    val awtImage = tex.createBufferedImage(TeXConstants.STYLE_DISPLAY, 26, java.awt.Color.BLACK, null)
    val fxImage = SwingFXUtils.toFXImage(awtImage.asInstanceOf[BufferedImage], null)
    fxImage
  }
}
