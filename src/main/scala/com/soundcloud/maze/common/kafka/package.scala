package com.soundcloud.maze.common

import com.typesafe.config.Config
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.{
  BOOTSTRAP_SERVERS_CONFIG,
  CLIENT_ID_CONFIG,
  KEY_SERIALIZER_CLASS_CONFIG,
  VALUE_SERIALIZER_CLASS_CONFIG
}
import org.apache.kafka.common.serialization.StringSerializer

import scala.jdk.CollectionConverters._

package object kafka {

  def kafkaProducer(config: Config): KafkaProducer[String, String] = {
    val props: Map[String, Object] = Map(
      CLIENT_ID_CONFIG              -> config.getString("topics.events"),
      BOOTSTRAP_SERVERS_CONFIG      -> config.getString("bootstrap-server"),
      KEY_SERIALIZER_CLASS_CONFIG   -> classOf[StringSerializer],
      VALUE_SERIALIZER_CLASS_CONFIG -> classOf[StringSerializer]
    )

    new KafkaProducer[String, String](props.asJava)
  }
}
