package com.akkastarting.chapter4

import akka.actor.{ActorLogging, Actor, Props}

class Ch4Ping2Actor extends Actor with ActorLogging {
  override def preRestart (reason: scala.Throwable, message: Option[scala.Any]) = {
    log.info("Ping2Actor preRestart, ...")
    log.info(s"Ping2Actor fail, reason -> ${message.getOrElse("-")}")
    log.info(s"Ping2Actor fail, reason -> ${reason.getMessage}")
  }

  override def postRestart (reason: scala.Throwable) = {
    log.info("Ping2Actor postRestart")
    log.info(s"Ping2Actor fail, reason -> ${reason.getMessage}")
  }

  override def postStop(): Unit = {
    log.info("Ping2Actor postStop")
  }

  def receive: Receive = {
    case message: Good =>
      goodWork()
      sender ! Done()
    case message: Bad =>
      badWork()
  }

  @throws(classOf[Exception])
  private def goodWork(): Unit = {
    log.info("Ping2Actor is Good")
  }

  @throws(classOf[Exception])
  private def badWork(): Unit = {
    val b: Int = 1 / 0

    log.info(s"B -> ${b}")
  }
}
