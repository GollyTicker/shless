name := "shless"

version := "1.0"

resolvers ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

// For Scala 2.10.x >= 2.10.2
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.chuusai" % "shapeless_2.10.4" % "2.0.0"
  // "com.chuusai" % "shapeless" % "2.0.0" cross CrossVersion.full  // Alternatively ...
)
