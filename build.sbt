name := "maze"

scalaVersion := "3.1.0"

val AkkaVersion = "2.6.18"

val compileDependencies = Seq(
  ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).cross(CrossVersion.for3Use2_13)
).map(_ % Compile)

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.2.11",
  ("com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion)
    .cross(CrossVersion.for3Use2_13),
).map(_ % Test)

libraryDependencies ++= compileDependencies ++ testDependencies
