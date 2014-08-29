package ehg.func

import spray.http.Uri

/* Uri ⇔ input */
trait EhgUri {
	type Input

	def uri(input: Input): Option[Uri]
	def input(uri: Uri): Option[Input]

	def unapply(uri: Uri) = input(uri)
}
