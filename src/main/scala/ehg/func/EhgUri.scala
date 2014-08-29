package ehg.func

import spray.http.Uri
import util.UriUtil._

abstract class EhgUri[Input] {
	val base: Uri = http/"g.e-hentai.org"

	protected def check(u: Uri): Boolean = {
		u.scheme == base.scheme &&
			u.host.address == base.host.address
	}

	def uri(input: Input): Option[Uri]
	def input(uri: Uri): Option[Input]

	def unapply(uri: Uri): Option[Input] = input(uri)
}
