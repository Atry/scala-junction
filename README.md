`scala-junction` is an utility that creates [NTFS junction points](https://en.wikipedia.org/wiki/NTFS_junction_point) on Windows for Scala or Java.
# How to use it?
If you use sbt, add following lines to you `build.sbt`:

	resolvers += "scala-junction-repo" at "http://atry.github.com/scala-junction/maven"
	
	libraryDependencies += "com.dongxiguo" %% "scala-junction" % "0.1-SNAPSHOT"

The [API](http://atry.github.com/scala-junction/api/index.html) of `scala-junction` is quite simple. There is only three public static functions:

	com.dongxiguo.junction.Junction.createJunction(new java.io.File("my-link"), new java.io.File("D:\\target"));
	
	// Or if you are using Java 7:
	com.dongxiguo.junction.java7.Junction.createJunction(java.nio.file.Paths.get("my-link"), java.nio.file.Paths.get("D:\\target"));
	
	// Create junction only if failed to create a symblic link:
	com.dongxiguo.junction.java7.Junction.createSymbolicLinkOrJunction(java.nio.file.Paths.get("my-link"), java.nio.file.Paths.get("D:\\target"));

# What's the difference between symblic links and junctions?
Java 7 supports symblic link. But symblic link can only be created on POSIX, or by elevated user on Windows Vista/7/2008. On the other hand, I tested `scala-junction` on Windows XP and Windows 7 (both elevated user and non-elevated user), and `scala-junction` worked for all of these platform. I guess `scala-junction` works on any Windows NT.