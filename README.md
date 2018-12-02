# Scairly

Scairly is an asynchronous, non-blocking Scala client for the REST API for air pollution data exposed by https://airly.eu.
It uses [Akka HTTP](https://doc.akka.io/docs/akka-http/current/client-side/index.html) for handling requests
and [Circe](https://github.com/circe/circe) for JSON processing.

### Installation

TODO: bintray deployment

### Basic Usage

```scala
import it.mikulski.scairly.Scairly

val scairly = new Scairly("MY_API_KEY")

for {
  installations = scairly.getNearestInstallations(50.0616719, 19.9374332)
} installations.foreach(println)
```
