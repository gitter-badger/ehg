package ehg.func

import util.HtmlUtil._
import util.UriUtil._
import spray.http.Uri

import scala.{collection => c}
import c.{mutable => m}
import scala.util.control.Exception._
import scala.xml._

trait EhgEnvTopic { env: EhgEnv =>
	object TopicUri extends EhgUri[EhgKey] {
		def apply(k: EhgKey) = allCatch opt {
			import k._
			val p: Uri.Query = Map("p" -> s"$page")
			root/"g"/s"$id"/token/?p
		}

		protected def in(u: Uri) = allCatch opt {
			val paths: Seq[String] = u.path
			paths(0).ensuring(_ == "g")
			val id = paths(1).toInt
			val token = paths(2)
			val page = u.query.get("p").map(_.toInt).getOrElse(0)
			EhgKey(id, token, page)
		}
	}

	class TopicHtml(val html: Node) extends PageUrlListing {
		val title: String = {
			html \\ "h1" find hasAttr("id", "gn") flatMap(_ textOp) get
		}

		val titleJP: Option[String] = {
			html \\ "h1" find hasAttr("id", "gj") flatMap(_ textOp)
		}

		val attributes: c.Map[String, String] = {
			html \\ "div" find hasAttr("id", "gdd") map { div =>
				val attrs = div flatMap(_ \\ "td") map(_ text)
				iter2map(attrs iterator) map { kv =>
					val (k, v) = kv
					(k dropRight 1, v)
				}
			} get
		}

		private def iter2map[A](seq: Iterator[A]): c.Map[A, A] = {
			val map = new m.HashMap[A, A]
			var key: Option[A] = None
			seq foreach { a =>
				key match {
					case None => key = Some(a)
					case Some(k) => map += ((k, a)); key = None
				}
			}
			map
		}

		val language: String = {
			attributes("Language")
		}

		val size: Int = {
			val str = attributes("Images")
			"""^(\d+)(?= @)""".r.findFirstIn(str).get.toInt
		}

		val images: Seq[ImgLink] = {
			(html \\ "div" find hasAttr("id", "gdt")) map { gdt =>
				gdt \\ "div" filter hasAttr("class", "gdtm") map { gdtm =>
					val uri = (gdtm \\ "a" \@ "href").head.text
					val img = (gdtm \\ "img").head
					val name = (img \@ "title").head.text
					val index = (img \@ "alt").head.text.replaceAll(",", "").toInt
					ImgLink(uri, index, name)
				}
			} get
		}
	}
}

case class EhgKey(id: Int, token: String, page: Int) {
	id.ensuring(_ >= 0)
	token.ensuring(t => t.length == 10 && t.matches("""\w+"""))
	page.ensuring(_ >= 0)
}

case class ImgLink(uri: Uri, index: Int, name: String)
