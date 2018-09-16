package controllers

import javax.inject.{Inject, Singleton}
import model.http.GetVehiclesRequest
import play.api.libs.json.{JsArray, JsNumber}
import play.api.mvc.{AbstractController, ControllerComponents}
import service.ScheduleService

@Singleton
class ApiController @Inject()(cc: ControllerComponents,
                              scheduleService: ScheduleService) extends AbstractController(cc) {

  def getVehicles(getVehiclesRequest: GetVehiclesRequest) = Action {
    import getVehiclesRequest._
    val ids = scheduleService.getVehicleIds(timestamp, x, y)
    Ok(JsArray(ids.map(JsNumber(_))))
  }

  def isLineDelayed(name: String) = Action {
    scheduleService.isLineDelayed(name) match {
      case Some(isDelayed) => Ok(isDelayed.toString)
      case None => NotFound
    }
  }

}
