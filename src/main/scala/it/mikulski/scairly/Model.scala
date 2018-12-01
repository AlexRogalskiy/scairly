package it.mikulski.scairly

import java.time.Instant

case class Installation(id: InstallationId, location: Location, address: Address, elevation: Double, airly: Boolean, sponsor: Sponsor)

case class Measurements(current: AveragedValues, history: AveragedValues, forecast: AveragedValues)

case class Location(latitude: Double, longitude: Double)

case class Address(country: String, city: String, street: String, number: String, displayAddress1: String, displayAddress2: String)

case class Sponsor(name: String, description: String, logo: String, link: String)

case class AveragedValues(fromDateTime: Instant, tillDateTime: Instant, values: Seq[Value], indexes: Seq[Index], standards: Seq[Standard])

case class Value(name: String, value: Double)

case class Index(name: String, value: Double, level: String, description: String, advice: String, color: String)

case class Standard(name: String, pollutant: String, limit: Double, percent: Double)