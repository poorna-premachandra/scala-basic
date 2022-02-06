package lectures.part8implicits

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MagnetPattern extends App {
  //use case of type class

  //this pattern is useful in method overloading

  class P2PRequest;

  class P2PResponse;

  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(response: P2PResponse): Int
    def receive(future: Future[P2PRequest]): Int
    //    def receive(future: Future[P2PResponse]): Int

    //Above is not valid. Compiler sees multiple methods having Future as param. This is called type erasure
  }


  //Let's re-write above API as follows

  //step 1
  trait MessageMagnet[Result] {
    def apply(): Result
  }

  //step 2
  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  //step 3
  implicit class FromP2PRequest(requestFuture: Future[P2PRequest]) extends MessageMagnet[Int] {
    def apply(): Int = {
      println("Handling P2P request")
      42
    }
  }

  implicit class FromP2PResponse(requestResponse: Future[P2PResponse]) extends MessageMagnet[Int] {
    def apply(): Int = {
      println("Handling P2P response")
      24
    }
  }

  //step 4
  //now we can overcome type erasure
  receive(Future(new P2PRequest))
  receive(Future(new P2PResponse))
}
