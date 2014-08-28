package ehg.api

import spray.http.Uri

trait EhgReader {
	trait Read[A] {
		def apply(k: A): Seq[Ehg]
	}
	implicit val key: this.Read[EhgKey]
	implicit val tag: this.Read[EhgTag]
	implicit val user: this.Read[EhgUser]
	implicit val category: this.Read[EhgCategory]
	
	def apply[A](k: A)(implicit read: this.Read[A]) = read(k)
}

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
	val images: Seq[Option[Uri]]
	val rating: Float
	val tags: Seq[EhgTag]
}

case class EhgKey(id: Int, token: String)
case class EhgTag(key: String, value: String)
case class EhgUser(name: String)
case class EhgCategory(name: String)
