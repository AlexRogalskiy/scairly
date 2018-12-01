lazy val circeV = "0.9.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "it.mikulski",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scairly",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"       %% "akka-http"        % "10.1.5",
      "com.typesafe.akka"       %% "akka-stream"      % "2.5.18",
      "de.heikoseeberger"       %% "akka-http-circe"  % "1.22.0",

      "io.circe"          %% "circe-core"    % circeV,
      "io.circe"          %% "circe-generic" % circeV,

      "org.scalatest"           %% "scalatest"    % "3.0.5" % Test
    )
  )
