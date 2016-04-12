Akka Starting using Scala
====

# Introduction
여기에 포함된 코드는 임백준님이 저술한 아카 시작하기 책에 포함된 예제 코드를 스칼라로 포팅한 것이다. 아카를 공부하기 위해서 
책을 구매하였는데 예제 코드 및 설명이 모두 Java 기반으로 되어 있어서 아쉬웠던 사람들을 위해서 작업을 해보았다.
 
# Criteria
* 모든 예제는 20초 뒤에 종료되도록 코드를 조금 변경하였다. 책에는 포함되지 않은 내용으로, 관련해서는 Chapter2 코드에서
주석으로 표기할 것이며 이 후에는 따로 주석을 추가하지 않는다.
* 한 프로젝트 내 모든 코드를 구현할 것이기 때문에 예제 파일에 챕터를 추가한다. 아래 예시를 참고한다.
    * Chapter2 PingActor -> Ch2PingActor
    * Chapter3 Ping1Actor -> Ch3Ping1Actor
* receive 함수를 구현할 때, Pattern Matching을 이용하여 구현하며, 메시지는 Case Class를 사용한다.
* build.sbt 파일에 mainClass를 기입하는 방식으로 원하는 챕터를 실행한다.
    * mainClass in Compile := Some("com.akkastarting.chapter2.Ch2Main") // Chapter2 예제를 실행한다 