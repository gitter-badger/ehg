package ehg.func.uri

import spray.http.Uri
import spray.http.Uri._

object UriUtil {
	sealed class Http(secured: Boolean) {
		val http = httpScheme(secured)
		def /(host: Host): Uri = s"$http://$host"
	}

	val http = new Http(false)
	val https = new Http(true)

	implicit def str2host(s: String): Host = Host(s)
	implicit def str2path(s: String): Path = Path(s)
	implicit def str2query(map: Map[String, String]): Query = Query(map)

	def normPath(p: Path): Path = p match {
		case pp if pp.startsWithSlash => pp
		case pp => Path.Slash(pp)
	}

	implicit def path2str(p: Path): IndexedSeq[String] = {
		p.toString().split(Path./.toString()).filter(_.nonEmpty)
	}

	implicit def str2path(s: Traversable[String]): Path = {
		s.foldLeft(Path.Empty: Path)((sum, nxt) => sum / nxt) dropChars 1
	}

	implicit class UriOps(val uri: Uri) extends AnyVal {
		def host: Host = uri.authority.host
		def ~/(p: Path) = uri withPath normPath(p)
		def ~?(q: Query) = uri withQuery q
		def /(p: Path) = ~/(uri.path ++ p)
		def /?(q: Query) = ~?((uri.query ++ q).toMap)
		def /#(f: String) = uri withFragment f
		def -/ = ~/(Path.Empty)
		def -? = ~?(Query.Empty)
		def -# = uri withoutFragment
	}
}
