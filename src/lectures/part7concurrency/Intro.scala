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


  //RACE CONDITION
  def runInParallel = {
    var x = 0 //shared resource

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }
  for (_ <- 1 to 10000) runInParallel



  //Solution to race condition
  class BankAccount(var amount: Int) { //shared resource (amount)
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price
//    println("I've bought " + thing)
//    println("my account is now " + account)
  }

  for (_ <- 1 to 1000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "phone", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(1000)
    println() //unexpected result. last executing thread overwriting the value
  }

  //solutions
  //1. use synchronized on resource (commonly used because of the flexibility)
  //2. use @volatile annotation on resource

  //using synchronized
  def busSafe(account: BankAccount, thing: String, price: Int) = {
    account.synchronized { //synchronizing on the shared resource
      account.amount -= price
      println("I've bought " + thing)
      println("my account is now " + account)
    }
  }

  //using volatile
  // class BankAccount(@volatile var amount: Int) { //shared resource (amount)
  //   override def toString: String = "" + amount
  // }


  //EXERCISE
  //1. construct 50 inception threads and print msgs with thread id in reverse order
  def printMessage(maxThreads: Int, threadId: Int = 1): Thread = new Thread(() => {
    if (threadId < maxThreads) {
      val newThread = printMessage(maxThreads, threadId + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread ${threadId}")
  })

  printMessage(50).start()

  //2.
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())

  //biggest value = 100
  //smallest value = 1


  //SIDENOTE :
  //In a sequencing problems such as sleep fallacy,
  //sleeping does not guarantee the order of thread execution(a thread which has put to sleep will sleep 'at least'(can take more time) the amount defined)
  //solution is to use thread join in a suitable manner
}
