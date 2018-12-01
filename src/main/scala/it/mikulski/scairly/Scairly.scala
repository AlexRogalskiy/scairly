package it.mikulski.scairly

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import scala.concurrent.Future

class Scairly(apiKey: String) extends FailFastCirceSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val http = new HttpClient(apiKey)

  def getInstallation(id: InstallationId): Installation = ???

  def getNearestInstallations(latitude: Double, longitude: Double, maxDistance: Option[Double], maxResults: Option[Int]): Future[Seq[Installation]] = {
    val d = maxDistance.getOrElse(3.0)
    val r = maxResults.getOrElse(1)
    for {
      response <- http.get("installations/nearest", Map("lat" -> latitude, "lng" -> longitude, "maxDistanceKM" -> d, "maxResults" -> r))
      unmarshalled <- Unmarshal(response).to[Seq[Installation]]
    } yield unmarshalled
  }

  def getMeasurements(installationId: InstallationId): Measurements = ???

  def getNearestMeasurements(latitude: Double, longitude: Double, maxDistance: Option[Double]): Measurements = ???

  def getInterpolatedMeasurements(latitude: Double, longitude: Double): Measurements = ???

}
