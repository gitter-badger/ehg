package ehg.func

import spray.http.Uri
import util.UriUtil._

class EhgEnv {
	protected val host: Uri = http/"g.e-hentai.org"

	protected def checkHost(u: Uri): Boolean = {
		u.scheme == host.scheme &&
			u.host.address == host.host.address
	}

	protected abstract class EhgUri[Input] {
		def uri(input: Input): Option[Uri]
		protected def in(uri: Uri): Option[Input]

		def input(uri: Uri) = if (checkHost(uri)) in(uri) else None
		def unapply(uri: Uri) = input(uri)
	}
}
