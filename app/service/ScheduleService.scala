package service

import java.time.LocalTime

import javax.inject.{Inject, Singleton}
import repo.DelayRepo

@Singleton
class ScheduleService @Inject()(delayRepo: DelayRepo) {

  /**
    * Returns the vehicle ids for a given time and coordinates
    */
  def getVehicleIds(time: LocalTime, x: Int, y: Int): Seq[Int] = {
    Seq.empty
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
