package ehg

private[ehg] object Foo {
	type Image = Array[Byte]
}

import ehg.Foo._

trait Ehg {
	val key: EhgKey

	def thumbnail: Image
	def title: String
	def titleJP: Option[String]
	def uploader: EhgUser
	def date: Long
	def language: String
	def category: EhgCategory
	def parent: EhgKey
	def amount: Int
	def size: Int
	def rating: Float
	def tags: Seq[EhgTag]
	def image(index: Int): Option[Image]

	def images = Stream from 0 take size map image
}

case class EhgKey(id: Int, token: String)
case class EhgTag(key: String, value: String)
case class EhgUser(name: String)
case class EhgCategory(name: String)

trait EhgBuilder {
	type Build[A] <: A => Seq[Ehg]

	implicit def key: Build[EhgKey]
	implicit def tag: Build[EhgTag]
	implicit def user: Build[EhgUser]
	implicit def category: Build[EhgCategory]

	def build[A](a: A)(implicit b: Build[A]): Seq[Ehg] = b(a)
	def buildGallery(k: EhgKey) = this key k headOption
}
