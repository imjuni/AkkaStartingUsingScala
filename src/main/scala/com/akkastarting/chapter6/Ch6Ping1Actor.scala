package com.akkastarting.chapter6

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Actor, ActorLogging}

class Ch6Ping1Actor extends Actor with ActorLogging {
  def receive = {
    case Hash(n) =>
      log.info(s"Ch6Ping1Actor received message, -> ${hashCode()} $n")
      work(n)
  }

  def work(n: Int) = {
    log.info(s"Ch6Ping1Actor working on ${hashCode()} $n")

    Thread.sleep(3 * 1000)
    log.info(s"Ch6Ping1Actor completed -> ${hashCode()} $n, ...")

//    context.system.scheduler.scheduleOnce(3.seconds) {
//      log.info(s"Ch6Ping1Actor completed -> ${hashCode()} $n, ...")
//    }
  }
}
