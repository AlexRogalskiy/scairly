package it.mikulski.scairly

import java.time.Instant

case class Installation(id: InstallationId, location: Location, address: Address, elevation: Double, airly: Boolean, sponsor: Sponsor)

case class Measurements(current: AveragedValues, history: AveragedValues, forecast: AveragedValues)

case class Location(latitude: Double, longitude: Double)

case class Address(country: Option[String], city: Option[String], street: Option[String], number: Option[String],
  displayAddress1: Option[String], displayAddress2: Option[String])

case class Sponsor(name: String, description: Option[String], logo: Option[String], link: Option[String])

case class AveragedValues(fromDateTime: Instant, tillDateTime: Instant, values: Seq[Value], indexes: Seq[Index], standards: Seq[Standard])

case class Value(name: String, value: Double)

case class Index(name: String, value: Double, level: String, description: String, advice: String, color: String)

case class Standard(name: String, pollutant: String, limit: Double, percent: Double)