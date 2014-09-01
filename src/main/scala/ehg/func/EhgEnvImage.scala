package ehg.func

import spray.http.Uri
import util.HtmlUtil._
import util.UriUtil._

import scala.util.control.Exception._
import scala.xml._

trait EhgEnvImage { env: EhgEnv =>
	object ImageUri extends EhgUri[EhgKey] {
		def apply(k: EhgKey) = allCatch opt {
			import k._
			root/"s"/token/s"$id-$page"
		}

		protected def in(uri: Uri) = allCatch opt {
			val paths: Seq[String] = uri.path
			paths(0).ensuring(_ == "s")
			val token = paths(1)
			val idpage = paths(2).split("-")
			val id: Int = idpage(0).toInt
			val page: Int = idpage(1).toInt
			EhgKey(id, token, page)
		}
	}

	class ImageHtml(val html: Node) {
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
}

