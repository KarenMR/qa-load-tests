package phone_scenarios

import InContactAPI.{AdminAPI, AgentApi, inContactAuth}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.{http, jsonPath, status}
import loadusers.LoadSimulationSetUp
import scala.concurrent.duration._

class inboundPhoneConferenceCalls {
  val agents = new AgentApi
  val admin = new AdminAPI
  val loadAgents = new LoadSimulationSetUp
  val auth = new inContactAuth

  val inboundPhoneConferenceCallLoadTest: ScenarioBuilder = scenario("Conference Calls")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v6.0"))
    .pause(4)
    .exec(agents.changeAgentState("Available", "","v2.0"))
    //.exec(admin.getScript("SpawnInboundCall", "v4.0"))
    //.exec(admin.spwanScript(admin.scriptId,loadAgents.phoneSkillId, "", "", "v4.0"))
    .pause(2 minutes)
    .exec(
      http("Get Next Events")
        .get(loadAgents.baseURL.concat("InContactAPI/services/v2.0/agent-sessions/").concat("${sessionId}").concat("/get-next-event"))
        .headers(auth.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"timeout": "10"}""")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[?(@.ContactID)].ContactID").exists.saveAs("contactId")))
    .exec(agents.holdContact(agents.sessionId, "${contactId}", "v2.0"))
      .pause(2 seconds)
    .exec(agents.dialAgentPhone(agents.sessionId, "4005150001", loadAgents.outboundPhoneSkill, "v10.0"))
    .pause(30 seconds)
    .exec(agents.conferenceCall(agents.sessionId, "v2.0"))
    .pause(10 minutes)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "true", "v2.0"))

}
