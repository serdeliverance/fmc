import cats.effect.IOApp
import cats.effect.IO

import cats.implicits._
object TraversalExample extends IOApp.Simple {

  val run =
    (1 to 100).map(aggregateInfo).toList.sequence.as(())

  def aggregateInfo(num: Int): IO[Int] = ???
}
