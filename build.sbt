name := "scala-junction"

organization := "com.dongxiguo"

version := "0.1.1-SNAPSHOT"

libraryDependencies += "net.java.dev.jna" % "platform" % "3.4.0"

libraryDependencies += "net.java.dev.jna" % "jna" % "3.4.0"

crossScalaVersions :=
  Seq("2.8.0", "2.8.1", "2.8.2",
      "2.9.0", "2.9.0-1", "2.9.1", "2.9.1-1",
      "2.10.0-M1", "2.10.0-M2")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo <<= (isSnapshot) { isSnapshot: Boolean =>
  if (isSnapshot)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots") 
  else
    Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}


pomExtra := (
  <url>https://github.com/Atry/scala-junction</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:Atry/scala-junction.git</url>
    <connection>scm:git:git@github.com:Atry/scala-junction.git</connection>
  </scm>
  <developers>
    <developer>
      <id>Atry</id>
      <name>杨博</name>
    </developer>
  </developers>)
// vim: expandtab shiftwidth=2 softtabstop=2 syntax=scala
