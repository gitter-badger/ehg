package ehg.func.html

import HtmlUtil._

import scala.xml.Node

class EhgHtmlImage(val html: Node) {
	val title: String = {
		(html \\ "h1" textOp) get
	}

	val image: String = {
		html \\ "img" \@ "src" map(_.text) find(_.length >= 30) get
	}

	val imageStatic: String = {
		val txt = "Generate a static forum image link"
		html \\ "a" find(_.text == txt) flatMap { a =>
			"""(?<=')(http.+?)(?=')""".r findFirstIn(a \@ "onclick" text)
		} get
	}

	val imageOriginal: Option[String] = {
		html \\ "div" filter hasAttr("class", "if") flatMap { div =>
			val a = (div \ "a").head
			if (!a.text.matches("^Download original")) None
			else Some(a.attribute("href").head.text)
		} headOption
	}
}
