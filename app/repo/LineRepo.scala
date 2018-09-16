package repo

import javax.inject.Singleton
import model.Line
import play.api.Logger
import util.Implicits.StringOps
import util.{CsvMapper, Resources}

import scala.util.control.NonFatal

@Singleton
class LineRepo {

  private val lineById: Map[Int, Line] = loadData().map { line => line.id -> line }.toMap

  def getById(id: Int): Option[Line] = {
    lineById.get(id)
  }

  private def loadData(): Seq[Line] = {
    try {
      CsvMapper.readAndMap(Resources.file("data/lines.csv"), {
        case id :: name :: Nil if id.isNonNegativeInt => Line(id.toInt, name)
        case invalidLineRow => throw new RuntimeException(s"Couldn't parse line from csv row: $invalidLineRow")
      })
    } catch {
      case NonFatal(ex) =>
        Logger.error("Couldn't load lines", ex)
        throw ex
    }
  }
}
