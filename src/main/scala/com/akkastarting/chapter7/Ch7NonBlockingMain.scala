package com.akkastarting.chapter7

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Props, ActorSystem}

object Ch7NonBlockingMain extends App {
  println("Chapter7 Non-Blocking Main")

  val system = ActorSystem("NonBlockingActorSystem")
  val nonblocking = system.actorOf(Props[Ch7NonBlockingActor], "Ch7NonBlockingActor")

  nonblocking ! Calculation(10)
  nonblocking ! Greeting("Hello?")

  system.scheduler.scheduleOnce(10.seconds) {
    system.terminate()
  }
}
