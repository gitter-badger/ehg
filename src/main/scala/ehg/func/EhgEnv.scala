package ehg.func

import spray.http.Uri
import util.UriUtil._

trait EhgEnv { self =>
	protected val host = http/"g.e-hentai.org"

	protected def checkHost(u: Uri) = {
		u.scheme == host.scheme &&
			u.host.address == host.host.address
	}

	protected abstract class EhgUri[Input] {
		def uri(input: Input): Option[Uri]
		protected def in(uri: Uri): Option[Input]

		def input(uri: Uri) = {
			if (self.checkHost(uri)) in(uri) else None
		}
		def unapply(uri: Uri) = input(uri)
	}
}

//object EhgEnv {
//	def apply() = new EhgEnv
//		with EhgEnvImage
//		with EhgEnvTopic
//		with EhgEnvIndex
//}
