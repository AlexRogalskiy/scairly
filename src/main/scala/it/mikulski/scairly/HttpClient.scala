package it.mikulski.scairly

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.coding.Gzip
import akka.http.scaladsl.model.headers.{ HttpEncodings, RawHeader }
import akka.http.scaladsl.model.{ HttpMethods, HttpRequest, HttpResponse, Uri }
import akka.stream.ActorMaterializer
import akka.util.ByteString

import scala.concurrent.{ ExecutionContext, Future }

class HttpClient(apiKey: String)(implicit val system: ActorSystem) {

  val http = Http(system)

  val baseUrl = "https://airapi.airly.eu/v2/"

  def get(path: String, params: Map[String, Any]): Future[HttpResponse] = {
    val query = params.map(p => s"${p._1}=${p._2}").mkString("&")
    val request = HttpRequest(HttpMethods.GET, uri = Uri(s"$baseUrl$path?$query"))
      .addHeader(RawHeader("apikey", apiKey))
      .addHeader(RawHeader("Accept", "application/json"))
      //.addHeader(RawHeader("Accept-Encoding", "gzip"))
    http.singleRequest(request)
  }

  private def responseBodyAsString(response: HttpResponse)(implicit mat: ActorMaterializer, ec: ExecutionContext): Future[String] =
    for {
      bs <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
      ds <- response.encoding match {
        case HttpEncodings.gzip => Gzip.decode(bs)
        case _ => Future.successful(bs)
      }
    } yield ds.utf8String

}
