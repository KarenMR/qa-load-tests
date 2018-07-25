package phone_scenarios

import InContactAPI.{AdminAPI, AgentApi, inContactAuth}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


class inboundPhone extends Simulation {
  val agents = new AgentApi
  val admin = new AdminAPI
  val loadAgents = new LoadSimulationSetUp
  val auth = new inContactAuth

  val inboundPhoneLoadTest = scenario("Inbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v6.0"))
    .pause(50 seconds)
    .exec(agents.changeAgentState("Available", "","v2.0"))
    .pause(11 minutes)
    .exec(session =>{
      println("55555")
      println(agents.sessionId)
      session
    })
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "true", "v2.0"))
}



