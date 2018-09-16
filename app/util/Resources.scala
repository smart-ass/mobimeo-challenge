package util

import java.io.File

object Resources {

  def file(fileName: String): File = {
    val url = getClass.getClassLoader.getResource(fileName)
    new File(url.toURI)
  }
}
