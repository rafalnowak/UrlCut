package info.rnowak.urlcut

import java.nio.charset.Charset

import akka.actor.ActorSystem
import com.google.common.hash.Hashing
import com.redis.RedisClient
import info.rnowak.urlcut.domain.Url
import spray.routing.SimpleRoutingApp
import info.rnowak.urlcut.domain.UrlProtocol._
import spray.httpx.SprayJsonSupport._

object UrlCut extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem("UrlCut-system")
  val redisClient = new RedisClient(host = "localhost", port = 6379)

  startServer(interface = "localhost", port = 8080) {
    get {
      path("url" / Segment) { url =>
        complete {
          redisClient.get(url).get
        }
      }
    } ~
    post {
      path("url") {
        entity(as[Url]) { url =>
          complete {
            val urlHash = getHashForUrl(url)
            redisClient.set(getHashForUrl(url), url.url)
            urlHash
          }
        }
      }
    }
  }

  def getHashForUrl(url: Url): String = Hashing.murmur3_32().hashString(url.url, Charset.forName("UTF-8")).toString
}
