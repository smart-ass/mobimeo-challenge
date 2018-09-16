package controllers

import java.time.LocalTime

import model.http.GetVehiclesRequest
import org.scalatest.{Assertion, MustMatchers}
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.{JsArray, JsNumber}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ApiControllerSpec extends PlaySpec with SpecWithPlayApp with MustMatchers {

  private val controller = injector.instanceOf[ApiController]

  "UserController#getVehicles" should {

      "respond with the list of the vehicles for a given time and coordinates" in {

        def test(request: GetVehiclesRequest, expectedVehicleIds: Seq[Int]): Assertion = {
          val result = call(controller.getLines(request), FakeRequest(method = "GET", path = "/lines"))
          status(result) mustBe Status.OK
          val json = contentAsJson(result)
          json mustBe JsArray(expectedVehicleIds.sorted.map(JsNumber(_)))
        }

        test(GetVehiclesRequest(LocalTime.parse("10:01:00"), 1, 1), Seq(0))
        test(GetVehiclesRequest(LocalTime.parse("10:06:00"), 3, 4), Seq(1))
      }
  }

  "UserController#isLineDelayed" when {

    "line doesn't exist" should {
      "respond with 404" in {
        val nonExistingLine = "14"
        val result = call(
          controller.isLineDelayed(nonExistingLine),
          FakeRequest(method = "GET", path = s"/lines/$nonExistingLine")
        )

        status(result) mustBe Status.NOT_FOUND
      }
    }

    "line is currently delayed" should {
      "respond with true" in {
        val lineName = "M4"
        val result = call(
          controller.isLineDelayed(lineName),
          FakeRequest(method = "GET", path = s"/lines/$lineName")
        )

        status(result) mustBe Status.OK
        val content = contentAsString(result)
        content mustBe "true"
      }
    }

    "line is currently not delayed" should {
      "respond with false" in {
        val lineName = "Test"
        val result = call(
          controller.isLineDelayed(lineName),
          FakeRequest(method = "GET", path = s"/lines/$lineName")
        )

        status(result) mustBe Status.OK
        val content = contentAsString(result)
        content mustBe "false"
      }
    }
  }
}
