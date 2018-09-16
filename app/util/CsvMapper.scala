package util

import java.io.File

import com.github.tototoshi.csv.CSVReader

object CsvMapper {

  // Generally throwing an exception in langs like scala is a bad idea.
  // There are few ways to declare an error in the result type (eg Try, IO monad, etc)
  // It's done like this just for the sake of simplicity.

  /**
    * @throws Exception
    */
  def readAndMap[T](file: File, mapFunc: List[String] => T): List[T] = {
    // I assume that dataset is small and fits into memory.
    // In the real world we should probably put the data into a suitable storage
    // or use some kind of stream processing.
    CSVReader
      .open(file)
      .all()
      .tail // skip headers
      .map(mapFunc)
  }
}
