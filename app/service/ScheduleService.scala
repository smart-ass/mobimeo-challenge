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
    for {
      stop <- stopRepo.findByCoordinates(x, y)
      stopTiming <- stopTimingRepo.findById(stop.id)
      line <- lineRepo.getById(stopTiming.lineId)
      delayInMins = delayRepo.findByLine(line.name).map(_.value).getOrElse(0)
      correctedTime = stopTiming.time.plusMinutes(delayInMins)
      if correctedTime.equals(time)
    } yield line.id
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
