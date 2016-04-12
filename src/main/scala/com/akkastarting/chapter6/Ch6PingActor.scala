package com.akkastarting.chapter6

import akka.actor.{Props, Actor, ActorLogging}
import akka.routing.RoundRobinPool

class Ch6PingActor extends Actor with ActorLogging {
  val childrenPool = context.actorOf(RoundRobinPool(5).props(Props[Ch6Ping1Actor]), "Ping1Actor")

  def receive = {
    case Start() =>
      /*
       * 이 부분이 잘 이해가 되지 않아서 혼란스러웠다. 사실 알고보면 간단한데, Pool에 대한 개념이 없어서 더 그랬던 것 같다.
       * 아래 코드를 보면 Pool에 직접 메시지를 전달하는 것을 볼 수 있는데 이렇게 Pool에 메시지를 전달하면 Pool에서
       * 정해진 알고리듬에 의해서 Actor를 선택한 뒤 그 Actor으로 메시지를 송신해준다.
       *
       * 그래서 책 예제를 보면 Class HashCode를 출력하는 것을 볼 수 있는데, 본 프로젝트에서는 좀 더 드라마틱하게
       * 결과를 볼 수 있도록 책과 다르게 결과를 13개로 늘렸고, 각 Actor는 3초간 대기를 하도록 하였다.
       *
       * 그리고 본 예제에서는 처음으로 Thread.sleep를 사용하였는데, 이렇게 한 이유는 RoundRobinPool이 RoundRobin으로
       * Pool을 순회하는 것을 좀 더 쉽게 보기 위해서이다. 기존 방법을 사용하면 ForkJoinPool을 이용하여 ThreadPool으로
       * 작업을 실행하기 때문에 Pool이 순회하는 모습을 정확하게 보기 어렵다.
       */
      1 to 13 foreach {
        i => childrenPool ! Hash(i)
      }

    case message: Any =>
      unhandled(message)
  }
}