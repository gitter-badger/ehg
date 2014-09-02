package ehg.func.html

import HtmlUtil._
import ehg.func.uri._

import scala.xml.Node

class EhgHtmlIndex(val html: Node) extends PageUrlListing {
	val topics: Seq[TopicLink] = {
		html \\ "div" filter hasAttr("class", "it5") map { div =>
			val a = (div \ "a").head
			val u = a.attribute("href").get.head.text
			TopicLink(u, a.text)
		}
	}
}
