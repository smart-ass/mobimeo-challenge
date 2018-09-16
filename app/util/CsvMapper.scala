package util

import java.io.File

import com.github.tototoshi.csv.CSVReader

object CsvMapper {

  /**
    * @throws Exception
    */
  def readAndMap[T](file: File, mapFunc: List[String] => T): List[T] = {
    CSVReader
      .open(file)
      .all()
      .tail // skip headers
      .map(mapFunc)
  }
}
