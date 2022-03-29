import akka.stream.scaladsl.Tcp
import com.soundcloud.maze.commons.globals.ApplicationResult.ApplicationResult
import com.soundcloud.maze.domain.entities.Event

trait NotificationService {

  // TODO change connection for something more specific
  def notify(connection: Tcp.IncomingConnection, event: Event): ApplicationResult[Unit]
}
