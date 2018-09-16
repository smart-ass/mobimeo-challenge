name := "MobimeoBackendChallenge"

version := "1.0"

lazy val `weltdechallenge` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

PlayKeys.devSettings := Seq("play.server.http.port" -> "8081")
