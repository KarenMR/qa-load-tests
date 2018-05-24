package phone_scenarios

import InContactAPI.{AgentApi, inContactAuth}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._

class OutboundPhone extends Simulation {

  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth

  val outboundPhoneTest: ScenarioBuilder = scenario("Outbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}","v6.0"))
    .pause(5 seconds)
    //.exec(agents.changeAgentState("Available","duh", "v2.0"))
    .exec(agents.dialAgentPhone(agents.sessionId,"4006750004", loadAgents.outboundPhoneSkill, "v10.0"))
    .pause(2 minutes)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "false", "true", "true", "v10.0"))
    setUp(outboundPhoneTest.inject(atOnceUsers(4000)))


}
