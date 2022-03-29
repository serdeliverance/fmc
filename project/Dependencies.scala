import sbt._

object Dependencies {

  object V {
    val akkaVersion         = "2.6.18"
    val catsEffectVersion   = "3.3.9"
    val mockitoScalaVersion = "1.17.5"
    val kafkaClientVersion  = "2.8.0"
    val circeVersion        = "0.14.1"
    val circeGenericsExtras = "0.14.1"
    val logbackVersion      = "1.2.11"
    val fs2Kafka            = "2.5.0-M2"

    val scalatestVersion       = "3.2.11"
    val munitVersion           = "0.7.29"
    val munitCatsEffectVersion = "1.0.7"
  }

  val akka               = "com.typesafe.akka" %% "akka-actor-typed"     % V.akkaVersion
  val akkaStream         = "com.typesafe.akka" %% "akka-stream"          % V.akkaVersion
  val kafkaClient        = "org.apache.kafka"   % "kafka-clients"        % V.kafkaClientVersion
  val fs2Kafka           = "com.github.fd4s"   %% "fs2-kafka"            % V.fs2Kafka
  val circe              = "io.circe"          %% "circe-core"           % V.circeVersion
  val circeParser        = "io.circe"          %% "circe-parser"         % V.circeVersion
  val circeGeneric       = "io.circe"          %% "circe-generic"        % V.circeVersion
  val circeGenericExtras = "io.circe"          %% "circe-generic-extras" % V.circeVersion
  val catsEffect         = "org.typelevel"     %% "cats-effect"          % V.catsEffectVersion
  val logbackClassic     = "ch.qos.logback"     % "logback-classic"      % V.logbackVersion

  val scalatest         = "org.scalatest"     %% "scalatest"           % V.scalatestVersion
  val mockitoScala      = "org.mockito"       %% "mockito-scala"       % V.mockitoScalaVersion
  val akkaStreamTestkit = "com.typesafe.akka" %% "akka-stream-testkit" % V.akkaVersion
  val munit             = "org.scalameta"     %% "munit"               % V.munitVersion
  val munitCatsEffect3  = "org.typelevel"     %% "munit-cats-effect-3" % V.munitCatsEffectVersion
}
