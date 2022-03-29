ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "io.serdeliverance"
ThisBuild / organizationName := "serdeliverance"

resolvers += "Confluent Repo" at "https://packages.confluent.io/maven"

val AkkaVersion         = "2.6.18"
val MockitoScalaVersion = "1.17.5"
val ScalatestVersion    = "3.2.11"
val KafkaClientVersion  = "2.8.0"
val AlpakkaKafkaVersion = "3.0.0"
val CirceVersion        = "0.14.1"
val LogbackVersion      = "1.2.11"

lazy val domain =
  project.in(file("domain"))

lazy val commons =
  project
    .in(file("commons"))
    .settings(commonDependencies)
    .dependsOn(domain)

lazy val `event-producer` =
  project
    .in(file("event-producer"))
    .settings(eventProducerDependencies)
    .dependsOn(domain, commons)

lazy val `event-consumer` =
  project
    .in(file("event-consumer"))
    .settings(eventConsumerDependencies)
    .dependsOn(domain, commons)

lazy val commonDependencies =
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed"     % AkkaVersion,
    "org.apache.kafka"   % "kafka-clients"        % KafkaClientVersion,
    "io.circe"          %% "circe-core"           % CirceVersion,
    "io.circe"          %% "circe-parser"         % CirceVersion,
    "io.circe"          %% "circe-generic"        % CirceVersion,
    "io.circe"          %% "circe-generic-extras" % CirceVersion
  )

lazy val eventProducerDependencies =
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed"     % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream"          % AkkaVersion,
    "org.apache.kafka"   % "kafka-clients"        % KafkaClientVersion,
    "io.circe"          %% "circe-core"           % CirceVersion,
    "io.circe"          %% "circe-parser"         % CirceVersion,
    "io.circe"          %% "circe-generic"        % CirceVersion,
    "io.circe"          %% "circe-generic-extras" % CirceVersion,
    "ch.qos.logback"     % "logback-classic"      % LogbackVersion,
    // Test
    "org.scalatest"     %% "scalatest"           % ScalatestVersion    % Test,
    "org.mockito"       %% "mockito-scala"       % MockitoScalaVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion         % Test
  )

lazy val eventConsumerDependencies =
  libraryDependencies ++= Seq(
    "io.circe"      %% "circe-core"           % CirceVersion,
    "io.circe"      %% "circe-parser"         % CirceVersion,
    "io.circe"      %% "circe-generic"        % CirceVersion,
    "io.circe"      %% "circe-generic-extras" % CirceVersion,
    "ch.qos.logback" % "logback-classic"      % LogbackVersion,
    // Test
    "org.scalatest"     %% "scalatest"           % ScalatestVersion    % Test,
    "org.mockito"       %% "mockito-scala"       % MockitoScalaVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion         % Test
  )
