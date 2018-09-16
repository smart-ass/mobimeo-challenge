package repo

import javax.inject.{Inject, Singleton}
import model.Line

@Singleton
class LineRepo {

  def getById(id: Int): Line = ???
}
