package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatestplus.play.PlaySpec
import play.api.Application
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder

trait SpecWithPlayApp { _: PlaySpec =>

  protected lazy val app: Application = GuiceApplicationBuilder().build()

  protected lazy val injector: Injector = app.injector

  protected lazy implicit val mat: ActorMaterializer = {
    implicit val system: ActorSystem = app.actorSystem
    ActorMaterializer()
  }
}
