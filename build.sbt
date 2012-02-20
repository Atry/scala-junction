name := "scala-junction"

organization := "com.dongxiguo"

version := "0.1-SNAPSHOT"

libraryDependencies += "net.java.dev.jna" % "platform" % "3.4.0"

libraryDependencies += "net.java.dev.jna" % "jna" % "3.4.0"

crossScalaVersions := Seq("2.8.0", "2.8.1", "2.9.1")

publishTo :=
  Some(Resolver.file("Github Pages", file("../scala-junction-gh-pages/maven")))

// vim: expandtab shiftwidth=2 softtabstop=2 syntax=scala
