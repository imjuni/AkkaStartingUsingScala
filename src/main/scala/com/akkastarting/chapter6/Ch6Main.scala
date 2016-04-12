package com.akkastarting.chapter6

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{ActorRef, Props, ActorSystem}

object Ch6Main extends App {
  val system = ActorSystem("Ch5ActorSystem")
  val ping = system.actorOf(Props[Ch6PingActor], "PingActor")

  (ping ! Start())(ActorRef.noSender)

  system.scheduler.scheduleOnce(20.seconds) {
    system.terminate()
  }
}
