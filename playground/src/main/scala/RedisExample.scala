import cats.effect.IOApp
import dev.profunktor.redis4cats.Redis
import cats.effect.IO
import dev.profunktor.redis4cats.connection.RedisClient
import dev.profunktor.redis4cats.effect.Log.Stdout._

object RedisExample extends IOApp.Simple {

  def run =
    Redis[IO].utf8("redis://localhost").use { redis =>
      for {
        _ <- redis.set("foo", "123")
      } yield ()
    }
}
