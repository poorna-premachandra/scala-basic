package lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure extends App {

  //creating success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER EXCEPTION"))

  println(aSuccess)
  println(aFailure)

  def unsafeMethod(): String = throw new RuntimeException("Unsafe Method Exception")

  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  //syntax sugar ; another way to write Try
  val anotherPotentialFailure = Try {
    //code that might thow exception
  }

  //WORKING with unsafe APIs - use Try as a wrapper
  def backupMethod(): String = "a valid result"
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  println(fallbackTry)

  //DESIGNING safe APIs - return exceptions wrapped in Failure and return results wrapped in Success
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  //functions on Try
  println(potentialFailure.isSuccess) // or .isFailure

  //map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10)) //return a failure


  //EXERCISE
  println("EXERCISE")

  val host = "localhost"
  val port = "8080"

  def renderHTML(page: String) = println(page)

  class Connection {
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url)) //get() wrapped around a Try
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Port already allocated")

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port)) //getConnection(host, port) wrapped around a Try
  }


  //if we get the html page from the connection, print it to the console

  //Method 1
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))
  possibleHTML.foreach(renderHTML)

  //Method 2
  HttpService.getSafeConnection(host, port).flatMap(connection => connection.getSafe("/home")).foreach(renderHTML)

  //Method 3 - for comprehension
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

}
