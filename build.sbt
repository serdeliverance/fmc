import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0"
ThisBuild / organization     := "io.serdeliverance"
ThisBuild / organizationName := "serdeliverance"

resolvers += "Confluent Repo" at "https://packages.confluent.io/maven"

lazy val domain =
  project
    .in(file("domain"))
    .settings(domainDependencies)

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

lazy val `follower-service` =
  project
    .in(file("follower-service"))
    .settings(followerServiceDependencies)
    .dependsOn(domain, commons)

lazy val domainDependencies =
  libraryDependencies ++= Seq(
    catsEffect
  )

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

lazy val followerServiceDependencies =
  libraryDependencies ++= Seq(
    fs2Kafka,
    catsEffect,
    redis4Cats,
    mockitoScala     % Test,
    munit            % Test,
    munitCatsEffect3 % Test
  )
