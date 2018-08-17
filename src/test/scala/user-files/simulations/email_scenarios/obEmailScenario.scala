package email_scenarios

import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp
import io.gatling.core.structure.ScenarioBuilder
import Configurations.Configurations._
import scala.concurrent.duration._

class obEmailScenario extends Simulation{
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth
  val patronAPI = new PatronAPI
  val token1 = incontact_header_Token

  val outboundEmailTest: ScenarioBuilder = scenario("Outbound Phone Test")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}","v2.0"))
    .pause(5 seconds)
    .exec(agents.changeAgentState("Available", "", "v2.0"))
    .pause(5 seconds)
    .exec(agents.createOBEmail(agents.sessionId, loadAgents.emailOBSkillId))
    .pause(5 seconds)
    .exec(agents.getContactId(agents.sessionId, "ContactId"))
    .pause(5 seconds)
    .exec(patronAPI.email_transcriptData())
    .pause(60 seconds)
    .exec(agents.sendOBEmail(agents.sessionId, agents.contactId, patronAPI.bodyHtml))
    //.exec(agents.sendOBEmail(agents.sessionId, agents.contactId))
    .pause(10 seconds)
    .exec(agents.endAgentSession(agents.sessionId))
    .pause(10 seconds)

}
