name := "UrlCut"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.2"

libraryDependencies ++= {
  val akkaV = "2.3.5"
  val sprayV = "1.3.1"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-testkit" % sprayV % "test",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.specs2" %% "specs2-core" % "2.3.11" % "test"
  )
}
