package com.akkastarting.chapter4

import akka.actor.{Props, ActorSystem}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/*
 * Chapter4에서는 Akka에서 제공하는 Fault Tolerance 전략에 대해서 학습한다.
 * 아쉽게도 책에서 제공하는 2.3.9 버전 코드와 본 프로젝트에서 사용하는 2.4.3 코드는
 * 구현과 사용에 큰 차이가 있다. 따라서 Akka.io에서 공식 문서를 참고해서 공부하는 것을
 * 추천하며, 책에서 제공하는 내용은 큰 흐름을 잡는 용도로 정독하면 좋을 것이다.
 */

object Ch4Main extends App {
  val system = ActorSystem("Chapter4System")
  val ping = system.actorOf(Props[Ch4PingActor], "PingActor")

  ping ! Bad()

  system.scheduler.scheduleOnce(10.seconds) {
    system.terminate()
  }
}
