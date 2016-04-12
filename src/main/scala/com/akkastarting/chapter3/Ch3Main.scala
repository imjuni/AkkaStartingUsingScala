package com.akkastarting.chapter3

/*
 * Chapter3는 Actor 간 계층구조(Hierarchy)가 어떻게 형성 되는지를 학습하기 위해서 작성하는 예제이다.
 * 향후 대부분 예제는 Chapter3와 동일한 계층구조로 만들어진다.
 */

import akka.actor.{ActorSystem, Props}

object Ch3Main extends App {
  val system = ActorSystem("PingSystem")
  val ping = system.actorOf(Props[Ch3PingActor], "PingActor")

  ping ! Work("ping start working!")
}
