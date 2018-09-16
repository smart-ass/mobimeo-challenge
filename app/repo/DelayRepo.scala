package repo

import javax.inject.Singleton
import model.Delay
import play.api.Logger
import util.{CsvMapper, Resources}
import util.Implicits.StringOps

import scala.util.control.NonFatal

@Singleton
class DelayRepo {

  private val delayByLine: Map[String, Delay] = loadDelays().map { delay => delay.lineName -> delay }.toMap

  def findByLine(lineName: String): Option[Delay] = {
    delayByLine.get(lineName)
  }

  private def loadDelays(): Seq[Delay] = {
    try {
      CsvMapper.readAndMap(Resources.file("data/delays.csv"), {
        case lineName :: delay :: Nil if delay.isNonNegativeInt => Delay(lineName, delay.toInt)
        case invalidDelayRow => throw new RuntimeException(s"Couldn't parse delay from csv row: $invalidDelayRow")
      })
    } catch {
      case NonFatal(ex) =>
        Logger.error("Couldn't load delays", ex)
        throw ex
    }
  }
}
