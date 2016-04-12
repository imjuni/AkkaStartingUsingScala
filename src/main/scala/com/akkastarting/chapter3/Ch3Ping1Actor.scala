package com.akkastarting.chapter3

import akka.actor.{ActorLogging, Actor, Props}

class Ch3Ping1Actor extends Actor with ActorLogging {
  val child2 = context.system.actorOf(Props[Ch3Ping2Actor], "Ping2Actor")
  val child3 = context.system.actorOf(Props[Ch3Ping3Actor], "Ping3Actor")

  def receive = {
    case Work(message) =>
      log.info("Ping1Actor, working")

      (child2 ! Work(message))(sender)
      (child3 ! Work(message))(sender)
  }
}
