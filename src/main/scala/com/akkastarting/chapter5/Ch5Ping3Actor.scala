package com.akkastarting.chapter5

import akka.actor.{ActorRef, ActorLogging, Actor, Props}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Ch5Ping3Actor extends Actor with ActorLogging {
  def receive: Receive = {
    case _ =>
      log.info(s"Message received, -> ")
      work(sender)
  }

  def work (sender: ActorRef): Unit =  {
    context.system.scheduler.scheduleOnce(1.seconds) {
      log.info("Ping3 Working")

      sender ! Done("Ping3 working Done")
    }
  }
}

object Ch5Ping3Actor {
  val props = Props[Ch5Ping3Actor]
}