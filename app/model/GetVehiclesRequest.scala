package model

import java.time.LocalTime
import java.time.format.DateTimeParseException

import play.api.mvc.QueryStringBindable


case class GetVehiclesRequest(timestamp: LocalTime, x: Int, y: Int)


object GetVehiclesRequest {
  
  implicit val localTimeBindable: QueryStringBindable[LocalTime] = new QueryStringBindable[LocalTime] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, LocalTime]] = {
      params.get(key).flatMap(_.headOption).map { timestampStr =>
        try {
          Right(LocalTime.parse(timestampStr))
        } catch {
          case _: DateTimeParseException =>
            Left(s"Unable to parse timestamp from: $timestampStr")
        }
      }
    }

    override def unbind(key: String, time: LocalTime): String = {
      time.toString
    }
  }

  implicit def requestBindable(implicit intBinder: QueryStringBindable[Int]): QueryStringBindable[GetVehiclesRequest] =
    new QueryStringBindable[GetVehiclesRequest] {

      override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, GetVehiclesRequest]] = {
        for {
          timestamp <- localTimeBindable.bind("timestamp", params)
          x <- intBinder.bind("x", params)
          y <- intBinder.bind("y", params)
        } yield {
          (timestamp, x, y) match {
            case (Right(timestampVal), Right(xVal), Right(yVal)) => Right(GetVehiclesRequest(timestampVal, xVal, yVal))
            case _ => Left(s"Unable to bind an GetVehiclesRequest from: $params")
          }
        }
      }

      override def unbind(key: String, request: GetVehiclesRequest): String = {
        import request._
        localTimeBindable.unbind(key, timestamp) + "&" + intBinder.unbind("x", x) + "&" + intBinder.unbind("y", y)
      }
    }
}