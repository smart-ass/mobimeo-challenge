package repo

import javax.inject.Singleton
import model.StopTiming

@Singleton
class StopTimingRepo {

  def findByIds(ids: Seq[Int]): Seq[StopTiming] = ???
}
