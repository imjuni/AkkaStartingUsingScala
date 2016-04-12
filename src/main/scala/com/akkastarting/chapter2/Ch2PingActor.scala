package com.akkastarting.chapter2

import akka.actor.{ActorLogging, Actor, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Ch2PingActor extends Actor with ActorLogging  {
  val pong = context.actorOf(Props(new Ch2PongActor(self)), name = "PongActor")

  def receive = {
    case Start(message) =>
      log.info(s"Ch2PingActor received message $message")

      pong ! Pong("pong-pong", 0)

    case Ping(message, count) =>
      log.info(s"Message -> $message / $count")

      // 새롭게 추가된 부분이다. 이 부분이 없다면 무한하게 Ping/Pong을 한다. 그래서 코드를 개선했다.
      // 30번 Ping/Pong 메시지를 주고 받은 뒤 ActorSystem을 종료한다
      if (count < 30)
        sender ! Pong("pong-pong", count + 1)
      else
        context.system.scheduler.scheduleOnce(3.seconds) {
          log.info("ActorSystem terminated after 3 seconds, ...")
          context.system.terminate()
        }
  }
}