package controllers

import javax.inject.Singleton
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result}
import play.api.mvc.Results.{InternalServerError, Status}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Logger.error(s"A client error occurred. Request=$request; statusCode=$statusCode; message=$message")
    Future.successful(Status(statusCode)(message))
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Logger.error(exception.getMessage, exception)
    Future.successful(InternalServerError("A server error occurred: " + exception.getMessage))
  }
}
