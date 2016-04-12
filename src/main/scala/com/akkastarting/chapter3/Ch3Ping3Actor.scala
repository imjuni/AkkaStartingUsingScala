package com.akkastarting.chapter3

import akka.actor.{ActorRef, Actor, ActorLogging, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Ch3Ping3Actor extends Actor with ActorLogging {
  def receive: Receive = {
    case Work(message) =>
      log.info("Ping3Actor message received, ...")
      work(sender)
  }

  def work(sender: ActorRef) = {
    log.info("Ping3Actor now working start, ...")

    context.system.scheduler.scheduleOnce(3.seconds) {
      log.info("Ping3Actor working done, ...")

      sender ! Done("Ping3Actor is done, ...")
    }
  }
}
