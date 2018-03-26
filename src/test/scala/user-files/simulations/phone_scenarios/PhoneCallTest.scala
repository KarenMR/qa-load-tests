package phone_scenarios

import InContactAPI.{AdminAPI, AgentApi, inContactAuth}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


class PhoneCallTest extends Simulation {
  val agents = new AgentApi
  val admin = new AdminAPI
  val loadAgents = new LoadSimulationSetUp
  val auth = new inContactAuth

  val inboundPhoneLoadTest = scenario("Inbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v6.0"))
    .pause(4)
    .exec(agents.changeAgentState("Available", "","v2.0"))
    .exec(admin.getScript("SpawnInboundCall", "v4.0"))
    .exec(admin.spwanScript(admin.scriptId,loadAgents.phoneSkillId, "", "", "v4.0"))
    .pause(60)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "true", "v2.0"))
  setUp(inboundPhoneLoadTest.inject(rampUsers(5) over (1 minutes)))
}



