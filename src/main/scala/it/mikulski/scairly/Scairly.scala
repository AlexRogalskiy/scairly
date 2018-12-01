package it.mikulski.scairly

class Scairly(apiKey: String) {

  def getInstallation(id: InstallationId): Installation = ???

  def getNearestInstallations(latitude: Double, longitude: Double, maxDistance: Option[Double], maxResults: Option[Double]): Seq[Installation] = ???

  def getMeasurements(installationId: InstallationId): Measurements = ???

  def getNearestMeasurements(latitude: Double, longitude: Double, maxDistance: Option[Double]): Measurements = ???

  def getInterpolatedMeasurements(latitude: Double, longitude: Double): Measurements = ???

}
