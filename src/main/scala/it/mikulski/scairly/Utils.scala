package it.mikulski.scairly

object Utils {

  // calculates great-circle distance between two points on a sphere
  def distance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double = {
    import scala.math._
    def haversine(v: Double): Double = pow(sin(v / 2), 2)
    val dLat = toRadians(lat2 - lat1)
    val dLng = toRadians(lng2 - lng1)
    val a: Double = haversine(dLat) + Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) * haversine(dLng)
    val c: Double = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    6371 * c
  }

}
