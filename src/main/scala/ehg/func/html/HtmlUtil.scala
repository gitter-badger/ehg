package ehg.func.html

import scala.xml._

object HtmlUtil {
	def str2op(s: String): Option[String] = s match {
		case "" => None
		case st => Some(st)
	}
	
	def toAttr(name: String): String = "@" + name

	def hasAttr(name: String, text: String): NodeSeq => Boolean = {
		(_: NodeSeq) \ toAttr(name) contains Text(text)
	}

	implicit class NodeSeqOp(val n: NodeSeq) extends AnyVal {
		def textOp: Option[String] = str2op(n.text)
		def \@(attr: String) = n \ toAttr(attr)
		def \\@(attr: String) = n \\ toAttr(attr)
	}
}
