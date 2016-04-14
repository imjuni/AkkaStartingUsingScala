package com.akkastarting.chapter7

import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Props, Actor, ActorLogging}
import akka.pattern.Patterns

import scala.util.{Failure, Success}

class Ch7NonBlockingActor extends Actor with ActorLogging {
  val timeout = Timeout(5.seconds)
  val child = context.system.actorOf(Props[Ch7CalculationActor], "Ch7CalculationActor")

  def receive = {
    case Calculation(n) =>
      Patterns.ask(child, Calculation(n), timeout) onComplete {
        case Success(n) =>
          println(s"Final result -> $n")
        case Failure(ex) =>
          println(s"Final result fail, -> ${ex.getMessage}")
      }
    case message: Any =>
      log.info("NonBlockingActor received some message")
  }
}
