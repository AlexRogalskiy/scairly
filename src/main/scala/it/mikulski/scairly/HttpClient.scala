package it.mikulski.scairly

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.coding.{ Gzip, NoCoding }
import akka.http.scaladsl.model.headers.{ HttpEncodings, RawHeader }
import akka.http.scaladsl.model.{ HttpMethods, HttpRequest, HttpResponse, Uri }
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.ActorMaterializer
import akka.util.ByteString

import scala.concurrent.{ ExecutionContext, Future }

class HttpClient(apiKey: String)(implicit val system: ActorSystem) {

  val http = Http(system)

  val baseUrl = "https://airapi.airly.eu/v2/"

  def get[T](path: String, params: Map[String, Any])
    (implicit unmarshaller: Unmarshaller[HttpResponse, T], ec: ExecutionContext, mat: ActorMaterializer): Future[T] = {
    val query = params.map(p => s"${p._1}=${p._2}").mkString("&")
    val request = HttpRequest(HttpMethods.GET, uri = Uri(s"$baseUrl$path?$query"))
      .addHeader(RawHeader("apikey", apiKey))
      .addHeader(RawHeader("Accept", "application/json"))
      .addHeader(RawHeader("Accept-Encoding", "gzip"))
    for {
      response <- http.singleRequest(request)
      parsed <- unmarshaller.apply(decode(response))
    } yield parsed
  }

  private def decode(response: HttpResponse): HttpResponse = {
    val decoder = response.encoding match {
      case HttpEncodings.gzip ⇒ Gzip
      case _ ⇒ NoCoding
    }
    decoder.decodeMessage(response)
  }

  private def responseBodyAsString(response: HttpResponse)(implicit mat: ActorMaterializer, ec: ExecutionContext): Future[String] = {
    for {
      bs <- decode(response).entity.dataBytes.runFold(ByteString(""))(_ ++ _)
    } yield bs.utf8String
  }

}
