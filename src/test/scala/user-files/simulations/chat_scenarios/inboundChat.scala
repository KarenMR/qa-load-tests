package com.chat_scenarios

import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


 class inboundChat extends Simulation {

  val auth = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val patron = new PatronAPI

  val ChatLoadTest = scenario("Chat Load")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v2.0"))
    .pause(10 seconds)
    .exec(patron.startChat(loadAgents.chatPOC, "v1.0"))
    .exec(agents.changeAgentState("Available", "", "v2.0"))
    .pause(20 seconds)
    //need to figure out how to do getnextevents
    .exec(
    http("Get Next Events")
      .get(loadAgents.baseURL.concat("InContactAPI/services/v2.0/agent-sessions/".concat("${sessionId}").concat("/get-next-event")))
      .headers(auth.auth_Token_ob.incontactHeaders())
      .body(StringBody("""{"timeout": "10"}""")).asJSON
      .check(status.is(200)).check(jsonPath("$.events[?(@.ContactID)].ContactID").exists.saveAs("contactId"))
  )
    .exec(session => {
      println("Get Next Events")
      println(session("sessionId").as[String])
      //println(session(baseURL.concat(str.toString())).as[String])
      println(session("contactId").as[String])
      session
    })
    .exec(agents.acceptContact(agents.sessionId, "${contactId}"))
    .pause(10 seconds)
    .exec(agents.endContact(agents.sessionId, "${contactId}", "v2.0"))
    .pause(20)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "false", "v2.0"))
}