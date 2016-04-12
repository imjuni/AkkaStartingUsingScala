package com.akkastarting.chapter2

import akka.actor.{ActorLogging, Actor, ActorRef, Props}

class Ch2PongActor(ping: ActorRef) extends Actor with ActorLogging {
  def receive = {
    case Pong(message, count) =>
      log.info(s"Message -> $message / $count")
      sender ! Ping("ping-ping", count + 1)
  }
}