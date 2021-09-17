package lectures.part7concurrency

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicReference

object ParallelUtils extends App {
  //1. parallel collections
  //non parallel data structure converted to parallel
  val parList = List(1, 2, 3).par  //not supported in scala 2.13 version. Module is separately available to import
  //parallel data structure
  val aParVector = ParVector[Int](1, 2, 3)

  /* parallel support also available for..
  Seq
  Vector
  Array
  Map - Hash, Trie
  Set - Hash, Trie
   */

  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }

  val list = (1 to 1000000).toList
  val serialTime = measure {
    list.map(_ + 1)
  }
  println("serial time: " + serialTime)

  val parallelTime = measure {
    list.par.map(_ + 1)
  }
  println("parallel time: " + parallelTime)

  //parallel is efficient for large datasets
  //non-parallel is efficient for small datasets(because thread management operations are expensive(i.e - takes time))

  //map, flatmap, filter, foreach, reduce, fold

  //be careful when using reduce and fold operations in parallel computing settings
  //fold, reduce with non-associative operators
  println(list(1, 2, 3).reduce(_ - _))
  println(List(1, 2, 3).par.reduce(_ - _))


  //synchronization
  var sum = 0
  List(1, 2, 3).par.foreach(sum += _)
  println(sum) //be careful! race conditions

  //configuring
  aParVector.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /*
  alternatives
  - ThreadPoolTaskSupport - deprecated now
  - ExecutionContextTaskSupport(EC)
   */

  //creating a custom tasksupport
  aParVector.tasksupoort = new TaskSupport {
    //have to override execute, executeAndWaitResult, parallelismLevel, environment
  }

  //2. atomic ops and references
  val atomic = new AtomicReference[Int](2)
  val currentValue = atomic.get() //thread-safe read
  atomic.set(4) //thread-safe write

  atomic.getAndSet(5) //thread safe combo

  atomic.compareAndSet(38, 56) //if the value is 38, then set to 56

  atomic.updateAndGet(_ + 1)
  atomic.getAndUpdate(_ + 1)

  atomic.accumulateAndGet(12, _ + _) //thread-safe function run
  atomic.getAndAccumulate(12, _ + _)
}
