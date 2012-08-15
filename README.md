`scala-junction` is an utility that creates [NTFS junction points](https://en.wikipedia.org/wiki/NTFS_junction_point) on Windows for Scala or Java.
# How to use it?
If you use sbt, add following lines to you `build.sbt`:

	libraryDependencies += "com.dongxiguo" %% "scala-junction" % "0.1.0"

The [API](http://atry.github.com/scala-junction/api/index.html) of `scala-junction` is quite simple. There are only three public static functions:

	com.dongxiguo.junction.Junction.createJunction(new java.io.File("my-link"), new java.io.File("D:\\target"));
	
	// Or if you are using Java 7:
	com.dongxiguo.junction.java7.Junction.createJunction(java.nio.file.Paths.get("my-link"), java.nio.file.Paths.get("D:\\target"));
	
	// Create junction only if failed to create a symblic link:
	com.dongxiguo.junction.java7.Junction.createSymbolicLinkOrJunction(java.nio.file.Paths.get("my-link"), java.nio.file.Paths.get("D:\\target"));

# What's the difference between symblic links and junctions?
Java 7 supports symblic links, but symblic links can only be created on POSIX, or by elevated user on Windows Vista/7/2008. On the other hand, `scala-junction` is tested working on Windows XP and Windows 7 (for both elevated user and non-elevated user). I guess `scala-junction` works on any version of Windows NT.