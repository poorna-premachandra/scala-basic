package lectures.part3fp

import scala.util.Random

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)
  println(noOption)

  //WORKING with unsafe APIs - use Option as a wrapper if we aren't getting an Option return from the unsafe method
  //unsafe API
  def unsafeMethod(): String = null
  //val result = Some(unsafeMethod()) // WRONG
  val result = Option(unsafeMethod()) //we don't do null checks our selves. Option do that for us

  //chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod())) //if unsafeMethod gives null, execute backupMethod

  //DESIGNING safe APIs - return Option
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod().orElse(betterBackupMethod())
  //val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()     can write like this also


  //functions on Options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get) //UNSAFE- Don't do this, can throw a NPE

  //map, flatMap, filter
  println(myFirstOption.map(_ * 2)) //Some(8)
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))



  //Exercise : try to establish a connection
  val config: Map[String, String] = Map(
    "host" -> "176.45.36.01",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected"
  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }


  //Method 1
  val host = config.get("host")
  val port = config.get("port")

  /*
  if (h != null)
    if (p != null)
      return Connection.apply(h, p)
   return null
   */
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))

  /*
  if (c != null)
    return c.connect
  return null
   */
  val connectionStatus = connection.map(c => c.connect)

  //if (connectionStatus == null) println(None) else print(Some(connectionStatus.get))
  println(connectionStatus)

  /*
  if (status != null)
    println(status)
   */
  connectionStatus.foreach(println)



  //Method 2 - chained calls
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)


  //Method 3 - for comprehension
  val forConnectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect
  forConnectionStatus.foreach(println)
}
