package com.akkastarting.chapter7

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{ActorRef, Actor, ActorLogging}

class Ch7CalculationActor extends Actor with ActorLogging {
  def receive = {
    case Calculation(n) =>
      log.info(s"CalculationActor received, -> $n")
      work(sender(), n)
  }

  def work (sender: ActorRef, n: Int) = {
    log.info(s"CalculationActor work start, ...")

    context.system.scheduler.scheduleOnce(3.seconds) {
      log.info(s"CalculationActor work complete, ...")
      sender ! Calculation(n * 2)
    }
  }
}