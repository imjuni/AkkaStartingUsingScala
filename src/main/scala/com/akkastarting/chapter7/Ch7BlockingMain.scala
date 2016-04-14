package com.akkastarting.chapter7

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Props, ActorSystem}

object Ch7BlockingMain extends App {
  println("Chapter7 Blocking Main")

  val system = ActorSystem("BlockingActorSystem")
  val blocking = system.actorOf(Props[Ch7BlockingActor], "Ch7BlockingActor")

  blocking ! Calculation(10)
  blocking ! Greeting("Hello?")

  system.scheduler.scheduleOnce(10.seconds) {
    system.terminate()
  }
}
