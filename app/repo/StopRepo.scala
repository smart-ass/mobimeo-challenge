package repo

import javax.inject.Singleton
import model.Stop
import play.api.Logger
import util.Implicits.StringOps
import util.{CsvMapper, Resources}

import scala.util.control.NonFatal

@Singleton
class StopRepo {

  private val stopByCoordinates: Map[(Int, Int), Seq[Stop]] = loadData().groupBy(stop => (stop.x, stop.y))

  def findByCoordinate(x: Int, y: Int): Seq[Stop] = {
    stopByCoordinates.getOrElse((x, y), Seq.empty)
  }

  private def loadData(): Seq[Stop] = {
    try {
      CsvMapper.readAndMap(Resources.file("data/stops.csv"), {
        case id :: x :: y :: Nil if id.isNonNegativeInt && x.isNonNegativeInt && y.isNonNegativeInt => Stop(id.toInt, x.toInt, y.toInt)
        case invalidStopRow => throw new RuntimeException(s"Couldn't parse stop from csv row: $invalidStopRow")
      })
    } catch {
      case NonFatal(ex) =>
        Logger.error("Couldn't load stops", ex)
        throw ex
    }
  }
}
