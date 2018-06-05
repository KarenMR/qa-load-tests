package email_scenarios


import InContactAPI.{AgentApi, inContactAuth}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp


class outboundEmail extends Simulation {
  val authy = new inContactAuth
  val loadAgen = new LoadSimulationSetUp
  val agents = new AgentApi

  val OutboundEmailTest = scenario("OutboundEmail Load")
    .exec(agents.changeAgentState("Available","","v6.0"))
    .exec(
      http("Get Next Events")
        .get(loadAgen.baseURL.concat("/InContactAPI/services/v2.0/agent-sessions/").concat("${sessionId}").concat("/get-next-event"))
        .headers(authy.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"timeout": "10"}""")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[?(@.ContactId)].ContactId").exists.saveAs("contactId")))
    .pause(10)
    .exec(agents.createEmail(agents.sessionId,"1149", "FreeLoader@Mailinator.com","v2.0"))
    .exec(agents.sendEmail(agents.sessionId,"${contactId}","1149", "FreeLoader@Mailinator.com", "loadTesting@LoadTest.com","","","LoadTesting", "Testing", "","","v4.0"))
    .pause(20)
}
