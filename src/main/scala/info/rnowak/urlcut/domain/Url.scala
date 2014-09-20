package info.rnowak.urlcut.domain

import spray.json.DefaultJsonProtocol

case class Url(url: String)

object UrlProtocol extends DefaultJsonProtocol {
  implicit val UrlFormat = jsonFormat1(Url)
}