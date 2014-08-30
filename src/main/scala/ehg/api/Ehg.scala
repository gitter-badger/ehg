package ehg.api

import spray.http.Uri

trait Ehg {
	val key: EhgKey
	val thumbnail: Uri
	val title: String
	val titleJP: Option[String]
	val uploader: EhgUser
	val date: Long
	val language: String
	val category: EhgCategory
	val parent: EhgKey
	val amount: Int
	val size: Int
	val rating: Float
	val tags: Seq[EhgTag]

	def image(index: Int): Either[Throwable, Uri]
	def images = Stream from 0 take size map image
}

case class EhgKey(id: Int, token: String)
case class EhgTag(key: String, value: String)
case class EhgUser(name: String)
case class EhgCategory(name: String)

trait EhgReader {
	trait Read[A] {
		def apply(k: A): Seq[Ehg]
	}
	def apply[A](k: A)(implicit read: this.Read[A]) = read(k)
}
