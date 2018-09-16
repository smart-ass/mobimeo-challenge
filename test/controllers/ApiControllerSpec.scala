package controllers

import java.time.LocalTime

import model.GetVehiclesRequest
import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.{JsBoolean, Json}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApiControllerSpec extends PlaySpec with SpecWithPlayApp with MustMatchers {

  private val controller = injector.instanceOf[ApiController]

  "UserController#getVehicles" when {

    "request is malformed" should {
      "respond with 400" in {
        val result = call(
          controller.getVehicles(null),
          FakeRequest(method = "GET", path = "/lines?timestamp=10:00:00")
        )
        status(result) mustBe Status.BAD_REQUEST
      }
    }

    "request is valid" should {
      "respond with the list of the vehicles for a given time and coordinates" in {
        val result = call(
          controller.getVehicles(GetVehiclesRequest(LocalTime.parse("10:00:00"), 10, 12)),
          FakeRequest(method = "GET", path = "/lines")
        )
        status(result) mustBe Status.OK
        val json = contentAsJson(result)
        json mustBe Json.obj("field" -> "value")
      }
    }
  }

  "UserController#isLineBusy" when {

    "line doesn't exist" should {
      "respond with 404" in {
        val nonExistingLine = "14"
        val result = call(
          controller.isLineBusy(nonExistingLine),
          FakeRequest(method = "GET", path = s"/lines/$nonExistingLine")
        )

        status(result) mustBe Status.NOT_FOUND
      }
    }

    "line is currently delayed" should {
      "respond with true" in {
        val lineName = "M4"
        val result = call(
          controller.isLineBusy(lineName),
          FakeRequest(method = "GET", path = s"/lines/$lineName")
        )

        status(result) mustBe Status.OK
        val json = contentAsJson(result)
        json mustBe JsBoolean(true)
      }
    }

    "line is currently not delayed" should {
      "respond with false" in {
        val lineName = "S75"
        val result = call(
          controller.isLineBusy(lineName),
          FakeRequest(method = "GET", path = s"/lines/$lineName")
        )

        status(result) mustBe Status.OK
        val json = contentAsJson(result)
        json mustBe JsBoolean(false)
      }
    }
  }
}
