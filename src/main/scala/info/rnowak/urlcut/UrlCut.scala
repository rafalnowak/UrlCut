package info.rnowak.urlcut

import akka.actor.ActorSystem
import info.rnowak.urlcut.domain.Url
import spray.routing.SimpleRoutingApp
import info.rnowak.urlcut.domain.UrlProtocol._
import spray.httpx.SprayJsonSupport._

object UrlCut extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem("UrlCut-system")

  startServer(interface = "localhost", port = 8080) {
    get {
      path("url" / Segment) { url =>
        complete {
          "Return full url for " + url
        }
      }
    } ~
    post {
      path("url") {
        entity(as[Url]) { url =>
          complete {
            "Shortening " + url
          }
        }
      }
    }
  }
}
