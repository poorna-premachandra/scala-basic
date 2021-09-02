package lectures.part7concurrency

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {

  //The producer consumer problem

  class SimpleContainer {
    private var value: Int = 0

    def isEmpty: Boolean = value == 0
    def set(newValue: Int) = value = newValue
    def get = {
      val result = value
      value = 0
      result
    }
  }


  //SINGLE RESOURCE, SINGLE CONSUMER AND PRODUCER
  //utilizing wait and notify thread methods
  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      container.synchronized {
        container.wait() //wait and notify methods can only be used inside synchronized block
      }
      println("[consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] Hard at work...")
      Thread.sleep(2000)
      val value = 42

      container.synchronized {
        println("[producer] I'm producing " + value)
        container.set(value)
        container.notify()
      }
    })

    consumer.start()
    producer.start()
  }

  //  smartProdCons()


  //MULTIPLE RESOURCES, SINGLE CONSUMER AND PRODUCER
  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()
      while (true) {
        buffer.synchronized {
          if (buffer.isEmpty) {
            println("[consumer] buffer empty, waiting...")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println("[consumer] consumed " + x)
          buffer.notify() //in case producer is waiting, wake it
        }
        Thread.sleep(random.nextInt(500))
      }
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0
      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("[producer] buffer is full, waiting...")
            buffer.wait()
          }
          println("[producer] producing " + i)
          buffer.enqueue(i)
          buffer.notify() //in case consumer is waiting, wake it
          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    })

    consumer.start()
    producer.start()
  }

  //  prodConsLargeBuffer()


  //MULTIPLE RESOURCES, MULTIPLE CONSUMERS AND PRODUCERS
  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      while (true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer $id] buffer empty, waiting...")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println(s"[consumer $id] consumed " + x)
          buffer.notify() //notify may received by a consumer or producer
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    val random = new Random()
    var i = 0
    override def run(): Unit = {
      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer $id] buffer is full, waiting...")
            buffer.wait()
          }
          println(s"[producer $id] producing " + i)
          buffer.enqueue(i)
          buffer.notify() //notify may received by a consumer or producer
          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  def multiProdCons(nConsumers: Int, nProducers: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    (1 to nConsumers).foreach(i => new Consumer(i, buffer).start())
    (1 to nProducers).foreach(i => new Producer(i, buffer, capacity).start())
  }

  multiProdCons(3, 3)

}
