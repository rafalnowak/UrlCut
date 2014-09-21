package info.rnowak.urlcut.service

import java.nio.charset.Charset

import akka.actor.Actor
import com.google.common.hash.Hashing
import com.redis.RedisClient
import info.rnowak.urlcut.domain.Url
import spray.http.StatusCodes
import spray.routing.HttpService
import info.rnowak.urlcut.domain.UrlProtocol._
import spray.httpx.SprayJsonSupport._

class UrlServiceActor extends Actor with UrlService {
  def actorRefFactory = context

  def receive = runRoute(urlRoute)
}

trait UrlService extends HttpService {
  val redisClient = new RedisClient(host = "localhost", port = 6379)

  val urlRoute =
    get {
      path(Segment) { url =>
        redirect(redisClient.get(url).get, StatusCodes.PermanentRedirect)
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

  def getHashForUrl(url: Url): String = Hashing.murmur3_32().hashString(url.url, Charset.forName("UTF-8")).toString
}
