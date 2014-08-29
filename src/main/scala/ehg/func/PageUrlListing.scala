package ehg.func

import scala.xml._
import util.HtmlUtil._

trait PageUrlListing {
	val html: Node

	protected val pagetds: Seq[Node] = {
		html \\ "table" find hasAttr("class", "ptt") map(_ \\ "td") get
	}

	protected def findtd(td: Node): PageLink = {
		val a = (td \ "a").head
		"^([0-9,]+)$".r findFirstIn a.text map { n =>
			val num = n replaceAll(",", "") toInt
			val url = a.attribute("href").get.head.text
			PageLink(num, url)
		} get
	}

	val pages: Map[Int, String] = {
		pagetds map { lnk =>
			val PageLink(num, url) = findtd(lnk)
			(num, url)
		} toMap
	}

	val current: PageLink = {
		pagetds find hasAttr("class", "ptds") map findtd get
	}

	val next: Option[String] = {
		pages get current.num + 1
	}
}

case class PageLink(num: Int, url: String)
