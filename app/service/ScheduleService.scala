package service

import java.time.LocalTime

import javax.inject.{Inject, Singleton}
import repo.{DelayRepo, LineRepo, StopRepo, StopTimingRepo}

@Singleton
class ScheduleService @Inject()(delayRepo: DelayRepo,
                                stopRepo: StopRepo,
                                stopTimingRepo: StopTimingRepo,
                                lineRepo: LineRepo) {

  /**
    * Returns the line ids for a given time and coordinates
    */
  def getLineIds(time: LocalTime, x: Int, y: Int): Seq[Int] = {
    val stops = stopRepo.findByCoordinate(x, y)
    val stopTimings = stopTimingRepo.findByIds(stops.map(_.id))

    stopTimings.flatMap { stopTiming =>
      val lineName = lineRepo.getById(stopTiming.lineId).name
      val delayInMins = delayRepo.findByLine(lineName).map(_.value).getOrElse(0)
      val correctedTime = stopTiming.time.plusMinutes(delayInMins)
      if (correctedTime.equals(time)) Some(stopTiming.lineId)
      else None
    }
  }

  /**
    * Checks if given line is currently delayed
    * @param line line name
    * @return None        - when line doesn't exist
    *         Some(value) - when line exists
    */
  def isLineDelayed(line: String): Option[Boolean] = {
    delayRepo.findByLine(line).map(_.value > 0)
  }
}
