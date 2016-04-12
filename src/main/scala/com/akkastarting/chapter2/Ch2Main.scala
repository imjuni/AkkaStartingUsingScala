package com.akkastarting.chapter2

import akka.actor.{Props, ActorSystem}

/*
 * Chapter2는 간단한 Ping/Pong 시스템을 구현하며 Akka가 어떤 시스템인지 감을 잡는 내용이다.
 */

object Ch2Main extends App {
  val system = ActorSystem("PingPongSystem")
  val ping = system.actorOf(Props[Ch2PingActor])

  ping ! Start("Start Ping-Pong System")
}
