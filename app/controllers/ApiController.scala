package controllers

import javax.inject.{Inject, Singleton}
import model.http.GetVehiclesRequest
import play.api.Logger
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import service.ScheduleService

@Singleton
class ApiController @Inject()(cc: ControllerComponents,
                              scheduleService: ScheduleService) extends AbstractController(cc) {

  def getVehicles(getVehiclesRequest: GetVehiclesRequest) = Action {
    Logger.debug(getVehiclesRequest.toString)
    Ok(Json.toJson(JsObject.empty))
  }

  def isLineDelayed(name: String) = Action {
    scheduleService.isLineDelayed(name) match {
      case Some(isDelayed) => Ok(isDelayed.toString)
      case None => NotFound
    }
  }

}
