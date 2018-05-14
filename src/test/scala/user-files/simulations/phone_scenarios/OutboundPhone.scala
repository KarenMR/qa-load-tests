package phone_scenarios

import InContactAPI.{AgentApi, inContactAuth}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._

class OutboundPhone extends Simulation {

  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth

  val outboundPhoneTest = scenario("Outbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}","v6.0"))
    .pause(5 seconds)
    //.exec(agents.changeAgentState("Available","duh", "v2.0"))
    .exec(agents.dialAgentPhone(agents.sessionId,"4005150001", loadAgents.outboundPhoneSkill, "v10.0"))
    .pause(50 seconds)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "false", "true", "true", "v10.0"))
    //setUp(outboundPhoneTest.inject(atOnceUsers(1)))


}
