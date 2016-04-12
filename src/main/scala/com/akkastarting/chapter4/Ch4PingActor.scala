package com.akkastarting.chapter4

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Props, ActorLogging, Actor}

class Ch4PingActor extends Actor with ActorLogging {
  val child1 = context.system.actorOf(Props[Ch4Ping1Actor], "Ping1Actor")

  def receive = {
    case message: Done =>
      log.info("PingActor receive Done, ...")
      log.info("All works are successfully completed")

      context.system.scheduler.scheduleOnce(5.seconds) {
        context.system.terminate()
      }
    case message: Good =>
      log.info("PingActor receive Good, ...")

      child1 ! message
    case message: Bad =>
      log.info("PingActor receive Bad, ...")

      child1 ! message
  }
}