package lectures.part7concurrency

import java.util.concurrent.Executors

object Intro extends App {
  
  val runnable = new Runnable{
    override def run(): Unit = println("Running in parallel")
  }
  val aThread = new Thread(runnable)

  aThread.start() //gives the signal to JVM to start a JVM thread
  //JVM creates threads on top of OS threads
  aThread.join() // makes sure the thread finishes running

  //thread pools - since starting and killing threads are expensive we re-use threads using a thread pool
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool")) //executes by 1 of the 10 threads managed by the pool

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 sec")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 sec")
  })

  pool.shutdown() //no more actions can be submitted(throw an exception), but already started threads will continue until finishes.
  //pool.execute(() => println("after shutdown")) //will throw exception

  //pool.shutdownNow() //similar to pool.shutdown() + already running threads will also throw exceptions

  println(pool.isShutdown) //check whether shutdown is called, returns boolean
}
