package ehg.func

import util.HtmlUtil._
import util.UriUtil._
import spray.http.Uri

import scala.util.control.Exception._
import scala.xml._

trait EhgEnvIndex { self: EhgEnv =>
	//object IndexUri extends EhgUri {
	//
	//}

	class IndexHtml(val html: Node) extends PageUrlListing {
		val topics: Seq[TopicLink] = {
			html \\ "div" filter hasAttr("class", "it5") map { div =>
				val a = (div \ "a").head
				val u = a.attribute("href").get.head.text
				TopicLink(u, a.text)
			}
		}
	}
}

case class TopicLink(url: String, title: String)
