name := "maze"

scalaVersion := "2.13.8"

resolvers += "Confluent Repo" at "https://packages.confluent.io/maven"

val AkkaVersion         = "2.6.18"
val MockitoScalaVersion = "1.17.5"
val ScalatestVersion    = "3.2.11"
val KafkaClientVersion  = "2.8.0"
val CirceVersion        = "0.14.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed"     % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream"          % AkkaVersion,
  "org.apache.kafka"   % "kafka-clients"        % KafkaClientVersion,
  "io.circe"          %% "circe-core"           % CirceVersion,
  "io.circe"          %% "circe-parser"         % CirceVersion,
  "io.circe"          %% "circe-generic"        % CirceVersion,
  "io.circe"          %% "circe-generic-extras" % CirceVersion,
  // Test
  "org.scalatest"     %% "scalatest"           % ScalatestVersion    % Test,
  "org.mockito"       %% "mockito-scala"       % MockitoScalaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion         % Test
)
