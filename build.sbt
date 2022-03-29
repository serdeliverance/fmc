import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "io.serdeliverance"
ThisBuild / organizationName := "serdeliverance"

resolvers += "Confluent Repo" at "https://packages.confluent.io/maven"

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

lazy val notificator =
  project
    .in(file("notificator"))
    .settings(notificatorDependencies)
    .dependsOn(domain, commons)

lazy val commonDependencies =
  libraryDependencies ++= Seq(
    akka,
    kafkaClient,
    circe,
    circeParser,
    circeGeneric,
    circeGenericExtras
  )

lazy val eventProducerDependencies =
  libraryDependencies ++= Seq(
    akka,
    akkaStream,
    kafkaClient,
    circe,
    circeParser,
    circeGeneric,
    circeGenericExtras,
    logbackClassic,
    scalatest         % Test,
    mockitoScala      % Test,
    akkaStreamTestkit % Test
  )

lazy val notificatorDependencies =
  libraryDependencies ++= Seq(kafkaClient)
