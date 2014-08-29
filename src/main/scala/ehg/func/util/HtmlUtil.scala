package ehg.func.util

import scala.xml._

object HtmlUtil {
	def str2op(s: String): Option[String] = s match {
		case "" => None
		case st => Some(st)
	}

	def hasAttr(name: String, text: String): NodeSeq => Boolean = {
		(_: NodeSeq) \ s"@$name" contains Text(text)
	}

	implicit class NodeSeqOp(val n: NodeSeq) extends AnyVal {
		def textOp: Option[String] = str2op(n.text)
		def \@(s: String) = n \ s"@$s"
		def \\@(s: String) = n \\ s"@$s"
	}
}
