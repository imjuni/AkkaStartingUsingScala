package com.akkastarting.chapter4

import scala.concurrent.duration._
import akka.actor.SupervisorStrategy.{Escalate, Stop, Restart, Resume}
import akka.actor.{Actor, ActorLogging, Props, OneForOneStrategy}
import akka.pattern.{BackoffOptions, Backoff, BackoffSupervisor}

class Ch4Ping1Actor extends Actor with ActorLogging {
  log.info("Ping1Actor Created, ...")

  val child2Supervisor = BackoffSupervisor.props(createStrategy(Props[Ch4Ping2Actor], "Ping2Actor"))
  val child3Supervisor = BackoffSupervisor.props(createStrategy(Props[Ch4Ping3Actor], "Ping3Actor"))
  val child2 = context.system.actorOf(child2Supervisor, "Ping2ActorWithSupervisor")
  val child3 = context.system.actorOf(child3Supervisor, "Ping3ActorWithSupervisor")

  def receive = {
    case message: Bad =>
      (child2 ! message)(sender)
      (child3 ! message)(sender)
    case message: Good =>
      (child2 ! message)(sender)
      (child3 ! message)(sender)
  }

  def createStrategy(props: Props, name: String): BackoffOptions = {
    /*
     * 이 부분은 최근 Akka 2.4.3 문서를 참고 했을 때, 책에 있는 내용과 많이 달라서 아주 어려웠던 부분이다.
     * 책은 Akka 2.3.9를 기반으로 하고 있으며, 이 부분은 2.3.9와 2.4.3이 많이 다르다. 이 프로젝트는
     * 최신 버전을 기준으로 하는 것이 더 좋다고 판단하여, 2.4.3 기준으로 코드를 재작성 하였다.
     *
     * 아래 내용을 간단하게 설명하자면, 2.4.3에서는 기존과 같이 Supervisor를 통한 모니터링을 한층 더 강화하여
     * 모니터링 알고리즘을 추가했다. 아래 예제는 Backoff 알고리즘을 통해서 Supervising하는 예제이다. Akka
     * 공식 문서도 Backoff 밖에 다루지 않지만 실제로 Akka repo.을 확인해보면 더 많은 알고리즘이 구현되어 있음을
     * 알 수 있다.
     *
     * Backoff 알고리즘을 이용해서 모니터링을 하며, minBackoff, maxBackoff, randomFactor 등이 Backoff
     * 알고리즘 입력 값이다. 이 후 ManualReset을 하도록 BackoffOptions 객체를 설정하고 ManualReset을 할 때
     * OneForOneStrategy 전략을 사용하도록 구현한다.
     *
     * Scala라서 구현 코드가 많이 다른 느낌을 주는데, OneForOneStrategy는 몇 가지 옵션을 받는데 그 것을 기본
     * 값으로 설정하고 오류에 따라서 어떤 행동을 취할 것인지를 결정하는 코드 블록을 넘겨준다.
     */
    Backoff.onStop(props,
      childName = name,
      minBackoff = 3.seconds,
      maxBackoff = 30.seconds,
      randomFactor = 0.2)
      .withManualReset
      .withSupervisorStrategy(OneForOneStrategy() {
        case ex: ArithmeticException =>
          Resume
        case ex: NullPointerException =>
          Restart
        case ex: IllegalArgumentException =>
          Stop
        case _ =>
          Escalate
      })
  }
}
