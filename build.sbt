name := "ehg"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.4"

libraryDependencies += "nu.validator.htmlparser" % "htmlparser" % "1.4"

resolvers += "spray repo" at "http://repo.spray.io"

libraryDependencies += "io.spray" % "spray-client" % "1.2.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.4"