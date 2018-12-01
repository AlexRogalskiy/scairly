package it.mikulski.scairly

import scala.concurrent.duration._
import scala.concurrent.Await

object Test extends App {

  val scairly = new Scairly("MY_API_KEY")

  val krakow = (50.0616719, 19.9374332)

  val installations = Await.result(scairly.getNearestInstallations(krakow._1, krakow._2, Some(40), Some(500)), 15.seconds)

  installations.foreach { i =>
    val dist = Utils.distance(krakow._1, krakow._2, i.location.latitude, i.location.longitude)
    val addr1 = s"${i.address.street.getOrElse("-")} ${i.address.number.getOrElse("-")}"
    val addr2 = s"${i.address.displayAddress1.getOrElse("")} ${i.address.displayAddress2.getOrElse("")}"
    println(f"${i.id}%5s: $dist%.2f km, ${Seq(addr1, addr2).maxBy(_.length)}")
  }

  println("total retrieved: " + installations.size)

}
