# Scairly

Scairly is an asynchronous, non-blocking Scala client for the REST API for air pollution data exposed by https://airly.eu.
It uses [Akka HTTP](https://doc.akka.io/docs/akka-http/current/client-side/index.html) for handling requests
and [Circe](https://github.com/circe/circe) for JSON processing.

### Installation

TODO: bintray deployment

### Basic Usage

To use the client, you need to register at https://developer.airly.eu and receive an API key, which you later provide
to your `Scairly` instance

```scala
import it.mikulski.scairly.Scairly

val scairly = new Scairly("MY_API_KEY")

scairly.getNearestInstallations(coord._1, coord._2, maxResults = 5).onComplete { response =>
  response match {
    case Success(installations) =>
      installations.foreach(println)
    case Failure(e) =>
      println(s"exception occurred: $e")
  }
  scairly.close()
}
```
