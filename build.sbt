name := "UrlCut"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.2"

scalacOptions := Seq("-feature", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaVersion = "2.3.5"
  val sprayVersion = "1.3.1"
  Seq(
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-json" % "1.2.6",
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.specs2" %% "specs2-core" % "2.3.11" % "test",
    "net.debasishg" %% "redisclient" % "2.13",
    "com.google.guava" % "guava" % "18.0"
  )
}
