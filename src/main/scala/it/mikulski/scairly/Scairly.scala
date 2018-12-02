package it.mikulski.scairly

import akka.actor.{ ActorSystem, Terminated }
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }

class Scairly(apiKey: String) extends FailFastCirceSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val http = new HttpClient(apiKey)

  def getInstallation(id: InstallationId): Future[Installation] = {
    http.get[Installation](s"installations/$id", Map.empty)
  }

  def getNearestInstallations(latitude: Double, longitude: Double, maxDistance: Double = 3.0, maxResults: Int = 1): Future[Seq[Installation]] = {
    val params = Map("lat" -> latitude, "lng" -> longitude, "maxDistanceKM" -> maxDistance, "maxResults" -> maxResults)
    http.get[Seq[Installation]]("installations/nearest", params)
  }

  def getMeasurements(installationId: InstallationId): Future[Measurements] = {
    http.get[Measurements](s"measurements/installation", Map("installationId" -> installationId))
  }

  def getNearestMeasurements(latitude: Double, longitude: Double, maxDistance: Option[Double]): Future[Measurements] = {
    val d = maxDistance.getOrElse(3.0)
    http.get[Measurements](s"measurements/nearest", Map("lat" -> latitude, "lng" -> longitude, "maxDistanceKM" -> d))
  }

  def getInterpolatedMeasurements(latitude: Double, longitude: Double): Future[Measurements] = {
    http.get[Measurements](s"measurements/point", Map("lat" -> latitude, "lng" -> longitude))
  }

  def close(): Terminated = Await.result(for {
    _ <- http.close()
    res <- system.terminate()
  } yield res, 15.seconds)

}
