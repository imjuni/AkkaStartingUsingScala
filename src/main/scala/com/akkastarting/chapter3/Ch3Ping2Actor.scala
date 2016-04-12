package com.akkastarting.chapter3

import akka.actor.{ActorRef, Actor, ActorLogging, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Ch3Ping2Actor extends Actor with ActorLogging {
  def receive: Receive = {
    case Work(message) =>
      log.info("Ping2Actor message received, ...")

      // 명확하게 sender를 work에게 전달하지 않으면 sender는 ActorSystem으로
      // 대체된다. 즉, 서로 바라보게 되는 sender가 어떤 call-stack 상태에 있는지에
      // 따라서 다를 수 있다.
      work(sender)

      log.info("Ping2Actor work after - 2, ...")
  }

  def work(sender: ActorRef) = {
    log.info("Ping2Actor now working start, ...")

    /*
     * 책에서 보여준, Thread.sleep 대신 현실에서 쓸 수도 있는 scheduleOnce 으로
     * 대체했다. 그래서, 구현이 조금 다르게 변한다. 책에서는 Thread.sleep으로 실행을 완전히
     * 정지 시켰기 때문에 sender를 전달할 필요가 없었고, work 함수 실행 이 후 sender으로
     * 메시지를 전송하면 된다. 하지만 이렇게 할 경우, 3초 뒤에 전송되어야 하는 done 메시지가
     * 즉시 전송되기 때문에 callback 스타일로 sender를 전달하고, 3초 뒤 전달 받은 sender
     * 으로 메시지를 전송한다.
     *
     * "Ping2Actor work after - 2, ..." 메시지가 출력되는 시점을 살펴보면 이 이야기를
     * 이해할 수 있을 것이다.
     */
    context.system.scheduler.scheduleOnce(3.seconds) {
      log.info("Ping2Actor working done, ...")
      sender ! Done("Ping2Actor is done, ...")
    }
  }
}
