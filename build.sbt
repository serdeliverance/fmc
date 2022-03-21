name := "maze"

scalaVersion := "2.13.8"

val AkkaVersion         = "2.6.18"
val MockitoScalaVersion = "1.17.5"
val ScalatestVersion    = "3.2.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % AkkaVersion,
  // Test
  "org.scalatest"     %% "scalatest"           % ScalatestVersion    % Test,
  "org.mockito"       %% "mockito-scala"       % MockitoScalaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion         % Test
)
