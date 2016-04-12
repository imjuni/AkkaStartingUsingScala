package com.akkastarting.chapter5

import akka.actor.{ActorLogging, Actor, Props}

class Ch5Ping1Actor extends Actor with ActorLogging {
  val child2 = context.system.actorOf(Ch5Ping2Actor.props, "Ping2Actor")
  val child3 = context.system.actorOf(Ch5Ping3Actor.props, "Ping3Actor")

  def receive = {
    case message: Any =>
      log.info("Ch5PingActor message received, ...")

      (child2 ! message)(sender)
      (child3 ! message)(sender)
  }
}

/*
 * Scala에서는 Companion Object를 활용하면 아래와 같이 Props를 미리 생성해둘 수 있다.
 * 이렇게 하면 동적으로 Props를 생성하지 않고 아래 변수를 사용하기만 하면 된다. Actor 생성자에
 * 특별히 인자로 주어야 하는 변수가 없다면 이와 같이 사용하는 것도 한 가지 방법이 될 수 있다.
 */
object Ch5Ping1Actor {
  val props = Props[Ch5Ping1Actor]
}