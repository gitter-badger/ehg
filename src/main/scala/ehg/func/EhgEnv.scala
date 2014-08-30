package ehg.func

import spray.http.Uri
import util.UriUtil._

trait EhgEnv {
	protected val host: Uri = http/"g.e-hentai.org"

	protected def checkHost(u: Uri): Boolean = {
		u.scheme == host.scheme &&
			u.host.address == host.host.address
	}

	protected abstract class EhgUri[Input] {
		def uri(input: Input): Option[Uri]
		def input(uri: Uri): Option[Input]
		def unapply(uri: Uri) = input(uri)
	}
}
