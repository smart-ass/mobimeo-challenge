package controllers

import javax.inject.{Inject, Singleton}
import model.http.GetVehiclesRequest
import play.api.Logger
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getVehicles(getVehiclesRequest: GetVehiclesRequest) = Action {
    Logger.debug(getVehiclesRequest.toString)
    Ok(Json.toJson(JsObject.empty))
  }

  def isLineBusy(name: String) = Action {
    Ok(Json.toJson(Json.obj("result" -> true, "line" -> name)))
  }

}
