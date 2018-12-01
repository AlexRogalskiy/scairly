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

  def getInstallation(id: InstallationId): Installation = ???

  def getNearestInstallations(latitude: Double, longitude: Double, maxDistance: Option[Double], maxResults: Option[Int]): Future[Seq[Installation]] = {
    val d = maxDistance.getOrElse(3.0)
    val r = maxResults.getOrElse(1)
    val params = Map("lat" -> latitude, "lng" -> longitude, "maxDistanceKM" -> d, "maxResults" -> r)
    http.get[Seq[Installation]]("installations/nearest", params)
  }

  def getMeasurements(installationId: InstallationId): Measurements = ???

  def getNearestMeasurements(latitude: Double, longitude: Double, maxDistance: Option[Double]): Measurements = ???

  def getInterpolatedMeasurements(latitude: Double, longitude: Double): Measurements = ???

  def close(): Terminated = Await.result(for {
    _ <- http.close()
    res <- system.terminate()
  } yield res, 15.seconds)

}
