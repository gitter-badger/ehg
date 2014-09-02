package ehg.func.uri

import ehg.func.uri.UriUtil._
import spray.http.Uri

trait EhgUriBuilder { env =>
	val root: Uri = http/"g.e-hentai.org"

	protected def checkRoot(u: Uri) = {
		u.scheme == root.scheme &&
			u.host.address == root.host.address
	}

	trait EhgUri[Input] {
		def apply(input: Input): Option[Uri]
		protected def in(uri: Uri): Option[Input]

		def unapply(uri: Uri) = {
			if (env.checkRoot(uri)) in(uri) else None
		}
	}
}
