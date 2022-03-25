import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.Tcp
import com.soundcloud.maze.domain.entities.Event

object Session {

  final case class Notify(event: Event)

  def apply(userId: Long, connection: Tcp.IncomingConnection): Behavior[Notify] =
    Behaviors.receive { (context, message) =>
      context.log.info(s"Notifying user: $userId of event: ${message.event}")
      // TODO send message using socket
      Behaviors.same
    }
}
