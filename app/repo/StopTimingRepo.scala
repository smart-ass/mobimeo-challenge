package repo

import java.time.LocalTime

import javax.inject.Singleton
import model.StopTiming
import play.api.Logger
import util.Implicits.StringOps
import util.{CsvMapper, Resources}

import scala.util.Try
import scala.util.control.NonFatal

@Singleton
class StopTimingRepo {

  private val stopTimingsByStopId: Map[Int, Seq[StopTiming]] = loadData().groupBy(_.stopId)

  def findById(stopId: Int): Seq[StopTiming] = {
    stopTimingsByStopId.getOrElse(stopId, Seq.empty)
  }

  private def loadData(): Seq[StopTiming] = {
    try {
      CsvMapper.readAndMap(Resources.file("data/times.csv"), {
        case lineId :: stopId :: time :: Nil
          if lineId.isNonNegativeInt && stopId.isNonNegativeInt && Try(LocalTime.parse(time)).toOption.nonEmpty =>

          StopTiming(lineId.toInt, stopId.toInt, LocalTime.parse(time))
        case invalidStopTimingRow =>
          throw new RuntimeException(s"Couldn't parse stop timing from csv row: $invalidStopTimingRow")
      })
    } catch {
      case NonFatal(ex) =>
        Logger.error("Couldn't load stop timings", ex)
        throw ex
    }
  }
}
