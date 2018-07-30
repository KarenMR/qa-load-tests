package phone_scenarios

import InContactAPI.{AgentApi, inContactAuth}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import loadusers.LoadSimulationSetUp
import Configurations.Configurations._

import scala.concurrent.duration._

class outboundPhoneScenario extends Simulation{
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth
  val token1 = incontact_header_Token

  val outboundPhoneTest: ScenarioBuilder = scenario("Outbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}","v2.0"))
    .pause(5 seconds)
    .exec(agents.changeAgentState("Available", "", "v2.0"))
    .pause(10 seconds)
    .exec(agents.dialAgentPhone(agents.sessionId, loadAgents.outboundPhoneSkill, "v10.0"))
    .pause(10 seconds)
    .exec(agents.getContactId(agents.sessionId))
    .pause(10 seconds)
    .exec(agents.callRecord(agents.sessionId, agents.contactId))
    .pause(10 seconds)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "true", "true", "false", "v10.0"))
    .pause(10)
}
