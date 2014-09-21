package info.rnowak.urlcut.service

import java.nio.charset.Charset

import akka.actor.Actor
import com.google.common.hash.Hashing
import com.redis.RedisClient
import info.rnowak.urlcut.domain.Url
import spray.http.StatusCodes
import spray.routing.{ExceptionHandler, HttpService}
import info.rnowak.urlcut.domain.UrlProtocol._
import spray.httpx.SprayJsonSupport._
import spray.util.LoggingContext

class UrlServiceActor extends Actor with UrlService {
  def actorRefFactory = context

  def receive = runRoute(urlRoute)
}

trait UrlService extends HttpService {
  val redisClient = new RedisClient(host = "localhost", port = 6379)

  implicit def urlExceptionHandler(implicit log: LoggingContext) = {
    ExceptionHandler {
      case e:
        UrlNotFoundException => {
          ctx => {
            log.error("Url not found for request {}", ctx.request)
            ctx.complete(StatusCodes.NotFound, "Url not found")
          }
        }
    }
  }

  val urlRoute =
    get {
      path(Segment) { url =>
        val targetUrl = redisClient.get(url).getOrElse { throw new UrlNotFoundException() }
        redirect(targetUrl, StatusCodes.PermanentRedirect)
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
