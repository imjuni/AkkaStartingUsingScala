package com.akkastarting.chapter7

import akka.pattern.Patterns

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import akka.actor.{Props, Actor, ActorLogging}
import akka.util.Timeout

class Ch7BlockingActor extends Actor with ActorLogging {
  val child = context.system.actorOf(Props[Ch7CalculationActor])
  val timeout = new Timeout(5.seconds)
  val ec = context.system.dispatcher

  def receive = {
    case Calculation(n) =>
      val future = Patterns.ask(child, Calculation(n), timeout)
      val result = Await.result(future, timeout.duration)

      log.info(s"Final result -> $result")
    case _ =>
      log.info("BlockingActor received some message")
  }
}
