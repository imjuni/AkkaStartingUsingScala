package com.akkastarting.chapter2

import akka.actor.{ActorLogging, Actor, Props}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


/*
 * 내가 Scala로 예제를 포팅하면서 처음 가졌던 의문은 "왜 Java 예제에서는 UntypedActor를 상속해서
 * 사용하는데 Scala는 Actor를 사용해서 사용하는가?" 였다. 아직도 그 질문에 대해서 완벽하게 대답할 수는 없지만
 * 아는 만큼만 설명을 추가한다.
 *
 * Scala에서는 receive 함수를 사용하고, Java에서는 onReceive를 사용한다. onReceive를 보면
 * 인자를 Object 타입으로 받는 것을 볼 수 있다. 즉, onReceive에서 받아들이는 메시지가 UnTyped 이라는 의미이다.
 * 그래서 Object 타입을 받은 뒤, instanceof 연산을 통해서 원하는 메시지가 왔을 때 처리한다.
 *
 * Java에서는 instanceof는 비효율적인 연산이라고 한다. 다만 이 부분은 지속적으로 개선이 된 것으로 보여지는데,
 * 검색을 해보면 이런 문제는 2010년 경에서나 문제가 되었던 것 같고 현재는 거의 해결 된 것처럼 보인다.
 * 그래도 Akka에서는 메시지를 특정 타입으로 정해서 받는 TypedActor와 UnTypedActor를 구분해서 사용한다.
 *
 * Scala는 이러한 Type Matching을 극한 수준으로 끌어올렸으며 이 것이 Pattern Matching이라는 부분으로
 * 주요한 기능 중에 일부이다. 그래서 Actor라는 Scala 용 구현체를 쓰고 있으며 receive 함수는 Partial
 * Function으로, 대체로 Pattern Matching을 이용해서 Message를 판단해서 작업을 수행한다.
 *
 * 그래서 결론은, 구현 언어에 따른 차이이며 Scala에서는 아래와 같이 구현하는 것이 좀 더 자연스럽고
 * 좀 더 Scala-Way에 가깝다고 판단하여 아래와 같이 구현하였다. 앞으로 모든 예제도 이와 같은 방식으로
 * 구현하여 추가하였다.
 */
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