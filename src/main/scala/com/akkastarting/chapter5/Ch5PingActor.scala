package com.akkastarting.chapter5

import akka.actor.{ActorLogging, Actor, Props, Stash}

class Ch5PingActor extends Actor with ActorLogging with Stash {
  val child = context.system.actorOf(Ch5Ping1Actor.props, "Ping1Actor")

  /*
   * 아래는, 각각의 statement를 나타내는 함수이다. Scala는 pure functional language 이기 때문에,
   * 함수 자체를 값 다룰 수 있다. 따라서, receive 와 동일한 입출력을 가지는 함수를 여러 개를 만들고
   * 이를 message 처리 과정에서 사용할 handler로 지정하는 것이 가능하다.
   *
   * 반면, Java는 이와 같은 동작이 불가능하기 때문에 아카 시작하기 책을 보면, 각각의 handler를 객체로 만들고
   * 이렇게 만들어진 객체를 become으로 전달하는 것을 볼 수 있다.
   *
   * 또한 stash와 unstashAll도 다른 방식으로 사용한다. Java에서는 become, unbecome 등을 활용하기 위해서
   * UntypedActorWithStash 객체를 상속하는데 Scala에서는 이렇게 하는 것보다 효율적인 trait을 이용해서
   * Stash trait을 with 구문으로 쌓는(stack) 것을 볼 수 있다.
   */
  def zeroDone: Receive = {
    case Done(message: String) =>
      log.info(s"Received the first done -> $message")
      context.become(oneDone)
    case _ =>
      stash()
  }

  def oneDone: Receive = {
    case Done(message: String) =>
      log.info(s"Received the second done -> $message")
      unstashAll()
      context.become(allDone)
    case _ =>
      stash()
  }

  def allDone: Receive = {
    case Reset(message: String) =>
      log.info(s"Received a reset -> $message")
      context.become(receive)
    case _ =>
      stash()
  }

  def receive: Receive = {
    case Work(message: String) =>
      (child ! message)(self)
      context.become(zeroDone)
    case _ =>
      stash()
  }
}

object Ch5PingActor {
  val props = Props[Ch5PingActor]
}
