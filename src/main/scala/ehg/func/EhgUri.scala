package ehg.func

import spray.http.Uri
import util.UriUtil._

abstract class EhgUri[Input] {
	val base: Uri = http/"g.e-hentai.org"

	def uri(input: Input): Option[Uri]
	def input(uri: Uri): Option[Input]

	def unapply(uri: Uri): Option[Input] = input(uri)
}
