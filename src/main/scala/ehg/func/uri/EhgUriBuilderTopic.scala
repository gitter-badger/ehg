package ehg.func.uri

import UriUtil._
import spray.http.Uri

import scala.util.control.Exception._

trait EhgUriBuilderTopic { env: EhgUriBuilder =>
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
}

case class EhgKey(id: Int, token: String, page: Int) {
	id.ensuring(_ >= 0)
	token.ensuring(t => t.length == 10 && t.matches("""\w+"""))
	page.ensuring(_ >= 0)
}

case class ImgLink(uri: Uri, index: Int, name: String)
