package com.akkastarting.chapter5

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{ActorRef, ActorSystem}

/*
 * Chapter5에서는 Actor에 State를 부여하고, 이를 응용할 수  있도록 해주는 become, unbecome에
 * 대한 기본적인 개념을 학습한다. 이 부분 역시 2.4.3에서는 더 많은 부분이 추가 되었으므로, 추가된 기능이
 * 궁금하다면 Akka.io에서 제공하는 공식 문서를 참고한다. 본 프로젝트에서는 일단 책에서 언급하는 범위까지
 * 구현한다.
 */

object Ch5Main extends App {
  println("Chapter5 Statement-Machine")

  val system = ActorSystem("Ch5ActorSystem")
  val ping = system.actorOf(Ch5PingActor.props, "PingActor")

  (ping ! Work("Start working, ..."))(ActorRef.noSender)
  (ping ! Reset("Reset, ..."))(ActorRef.noSender)

  system.scheduler.scheduleOnce(10.seconds) {
    system.terminate()
  }
}
