package repo

import javax.inject.Singleton
import model.Stop

@Singleton
class StopRepo {

  def findByCoordinate(x: Int, y: Int): Seq[Stop] = ???
}
