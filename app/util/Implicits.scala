package util

object Implicits {

  implicit class StringOps(val s: String) extends AnyVal {

     def isNonNegativeInt: Boolean = {
       s.forall(_.isDigit)
     }

  }

}
