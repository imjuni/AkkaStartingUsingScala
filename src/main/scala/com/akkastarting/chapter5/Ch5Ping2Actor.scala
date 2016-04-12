package com.akkastarting.chapter5

import akka.actor.{ActorRef, ActorLogging, Actor, Props}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class Ch5Ping2Actor extends Actor with ActorLogging {
  def receive: Receive = {
    case _ =>
      log.info(s"Message received, -> ")

      // 이 부분은 주의를 요한다. receive 함수 내부에서 호출하는 sender는 Ch5Ping2Actor으로 메시지를 보낸
      // sender 인스턴스가 반환되지만, 이 것이 work를 실행해서 work 함수 내에서는 Ch5Ping2Actor으로 메시지를
      // 보낸 sender 인스턴스가 아니라, 기본 값인 ActorSytem을 반환한다. 이 부분은 테스트에 의해서 알게 된
      // 내용으로, receive가 호출 되면서 message를 전달하고 이 때 sender도 매핑이 됨을 추측할 수 있다.
      //
      // 따라서, 아래와 같이 sender를 넘겨 주어야 원하는 방식으로 동작한다.
      work(sender)
  }

  def work (sender: ActorRef): Unit =  {
    context.system.scheduler.scheduleOnce(1.seconds) {
      log.info("Ping2Actor Working")

      sender ! Done("Ping2Actor working Done")
    }
  }
}

object Ch5Ping2Actor {
  val props = Props[Ch5Ping2Actor]
}