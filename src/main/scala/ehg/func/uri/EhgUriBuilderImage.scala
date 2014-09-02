package ehg.func.uri

import UriUtil._
import spray.http.Uri

import scala.util.control.Exception._

trait EhgUriBuilderImage { env: EhgUriBuilder =>
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
}
