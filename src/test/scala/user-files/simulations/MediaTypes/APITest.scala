package com.MediaTypes




import InContactAPI.{AgentApi, inContactAuth}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._

class APITest extends Simulation {

  val auth = new inContactAuth
  val agent = new AgentApi
  val agentLoad = new LoadSimulationSetUp

  val changeState = scenario("Change State Test")
    .feed(agentLoad.feeder)
   // .exec(session => session.set("domain", "hc15"))
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agent.getAgentSession("${AgentPhone}","v6.0"))
    .pause(4)
    .exec(agent.changeAgentState("Available","", "v2.0"))
    .pause(4)
    .exec(agent.endAgentSession(agent.sessionId, "false", "false", "true", "v2.0"))
  setUp(changeState.inject(rampUsers(10) over (1 minute)))
}
