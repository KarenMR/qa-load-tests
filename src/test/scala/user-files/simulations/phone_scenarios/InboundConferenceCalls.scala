package phone_scenarios

import InContactAPI.{AdminAPI, AgentApi, inContactAuth}
import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, jsonPath, status}
import loadusers.LoadSimulationSetUp

class InboundConferenceCalls {
  val agents = new AgentApi
  val admin = new AdminAPI
  val loadAgents = new LoadSimulationSetUp
  val auth = new inContactAuth

  val inboundPhoneConferenceCallLoadTest = scenario("Conference Calls")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v6.0"))
    .pause(4)
    .exec(agents.changeAgentState("Available", "","v2.0"))
    .exec(admin.getScript("SpawnInboundCall", "v4.0"))
    .exec(admin.spwanScript(admin.scriptId,loadAgents.phoneSkillId, "", "", "v4.0"))
    .pause(60)
    .exec(
      http("Get Next Events")
        .get(loadAgents.baseURL.concat("InContactAPI/services/v2.0/agent-sessions/").concat("${sessionId}").concat("/get-next-event"))
        .headers(auth.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"timeout": "10"}""")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[?(@.ContactId)].ContactId").exists.saveAs("contactId")))

}
