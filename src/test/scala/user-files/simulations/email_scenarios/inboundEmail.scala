package email_scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp
import InContactAPI.{inContactAuth, AgentApi, EmailSMPT}

import scala.concurrent.duration._


class inboundEmail extends Simulation {

  val loadAgents = new LoadSimulationSetUp
  val auth = new inContactAuth
  val agents = new AgentApi
  val sendEmail = new EmailSMPT("1IBemail_masterBU@incontactemail.com", "", "", "nichole.proestakis@niceincontact.com", "hello", "Test", "HC-C15COR01")
  sendEmail.sendMessage

  val InboundEmail = scenario("InboundEmail Load")
    .exec(agents.changeAgentState("Available", "", "v6.0"))
    .pause(12 seconds)
    .exec(
      http("Get Next Events")
        .get(loadAgents.baseURL.concat("InContactAPI/services/v2.0/agent-sessions/").concat("${sessionId}").concat("/get-next-event"))
        .headers(auth.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"timeout": "10"}""")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[?(@.ContactId)].ContactId").exists.saveAs("contactId"))
    )
    .exec(session => {
      println("Get Next Events")
      println(session("sessionId").as[String])
      //println(session(baseURL.concat(str.toString())).as[String])
      println(session("contactId").as[String])
      session
    })
    .pause(10 seconds)
    .exec(agents.endContact(agents.sessionId, "${contactId}", "v2.0"))
    .pause(20)
}

