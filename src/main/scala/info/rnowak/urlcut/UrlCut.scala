package info.rnowak.urlcut

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import info.rnowak.urlcut.service.UrlServiceActor
import spray.can.Http
import spray.routing.SimpleRoutingApp

object UrlCut extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem("UrlCut-system")

  val service = actorSystem.actorOf(Props[UrlServiceActor], "url-service")

  IO(Http) ! Http.Bind(service, "localhost", port = 8080)
}
