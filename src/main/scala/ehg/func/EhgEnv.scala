package ehg.func

import spray.http.Uri
import util.UriUtil._

trait EhgEnv { env =>
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
