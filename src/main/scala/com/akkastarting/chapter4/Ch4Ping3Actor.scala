package com.akkastarting.chapter4

import akka.actor.{ActorLogging, Actor, Props}

class Ch4Ping3Actor extends Actor with ActorLogging {
  log.info("Ping2Actor Created, ...")

  override def preRestart (reason: scala.Throwable, message: Option[scala.Any]) = {
    log.info("Ch3Ping3Actor preRestart, ...")
    log.info(s"Ch3Ping3Actor fail, reason -> ${message.getOrElse("-")}")
    log.info(s"Ch3Ping3Actor fail, reason -> ${reason.getMessage}")
  }

  override def postRestart (reason: scala.Throwable) = {
    log.info("Ch3Ping3Actor postRestart")
    log.info(s"Ch3Ping3Actor fail, reason -> ${reason.getMessage}")
  }

  override def postStop(): Unit = {
    log.info("Ch3Ping3Actor postStop")
  }

  def receive: Receive = {
    case message: Good =>
      goodWork()
      sender ! Done()
    case message: Bad =>
      badWork()
  }

  @throws(classOf[Exception])
  private def goodWork() = {
    log.info("Ch3Ping3Actor is Good")
  }

  @throws(classOf[Exception])
  private def badWork() = {
    log.info("Ch3Ping3Actor is Bad")
    throw new NullPointerException()
  }
}
