package service

import javax.inject.{Inject, Singleton}
import repo.DelayRepo

@Singleton
class ScheduleService @Inject()(delayRepo: DelayRepo) {

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
