`scala-junction` is an utility that create [NTFS junction points](https://en.wikipedia.org/wiki/NTFS_junction_point) on Windows for Scala or Java.
# How to use it?
If you use sbt, add following lines to you `build.sbt`:

	resolvers += "scala-junction-repo" at "http://Atry.github.com/scala-junction/maven"
	
	libraryDependencies += "com.dongxiguo" %% "scala-junction" % "0.1-SNAPSHOT"

Then you can create junction points by invoking some static functions:

	com.dongxiguo.junction.Junction.createJunction(new java.io.File("my-link"), new java.io.File("""D:\target"""))
	
	// Or if you are using Java 7:
	com.dongxiguo.junction.java7.Junction.createJunction(java.nio.file.Path.get("my-link"), java.nio.file.Paths.get("""D:\target"""))

# Why not symblic link?
Java 7 supports symblic link. But symblic link can only be created for elevated user on Windows Vista/7/2008. On the other hand, I tested `scala-junction` on Windows XP and Windows 7 (elevated user and non-elevated user), and worked for all of these platform. I guess `scala-junction` works on any Windows NT.

There is a function that create junction when it is failed to create symbolic link:

	com.dongxiguo.junction.java7.Junction.createSymbolicLinkOrJunction(java.nio.file.Path.get("my-link"), java.nio.file.Paths.get("""D:\target"""))
