package com.akkastarting.chapter3

import akka.actor.{Actor, ActorLogging, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Ch3PingActor extends Actor with ActorLogging {
  var count: Int = 0
  val child = context.system.actorOf(Props[Ch3Ping1Actor], "Ping1Actor")

  def receive = {
    case Work(message) =>
      log.info("PingActor, Work it!")

      (child ! Work(message))(self)

    case Done(message) =>
      log.info("PingActor received done, ...")

      if (count == 0) {
        count = count + 1
      } else {
        log.info("All works complete, ...")
        count = 0

        context.system.scheduler.scheduleOnce(3.seconds) {
          context.system.terminate()
        }
      }

    case message: Any =>
      log.info("Unhandled message")
      unhandled(message)

  }
}
