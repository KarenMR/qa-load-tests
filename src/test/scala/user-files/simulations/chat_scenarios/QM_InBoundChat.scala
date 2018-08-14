package com.chat_scenarios

import io.gatling.core.Predef.{Simulation, StringBody, scenario}
import io.gatling.http.Predef.{http, jsonPath, status}
import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp
import scala.concurrent.duration._

class QM_InBoundChat extends Simulation {
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth
  val patron = new PatronAPI

  val QmChatLoadTest = scenario("Chat Load")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v2.0"))
    .pause(10 seconds)
    .exec(agents.changeAgentState("Available", "", "v2.0"))
    .pause(10 seconds)
    .exec(patron.startChat(loadAgents.chatPOC, "v9.0"))
    .pause(10 seconds)
    .exec(agents.getContactId(agents.sessionId))
    .pause(10 seconds)
    //.exec(agents.getContactId(agents.sessionId))
    //.exec(agents.acceptContact(agents.sessionId, "${contactId}", "v10.0"))
    .exec(agents.acceptContact(agents.sessionId, agents.contactId))
    .pause(10 seconds)
    .exec(patron.chat_transcriptData())
    .pause(10 seconds)
    //.exec(agents.sendChat(agents.sessionId,"${contactId}", "${Text}", "v10.0"))
    .exec(agents.sendChat(agents.sessionId,agents.contactId, "${Text}", "v10.0"))
    .pause(10 seconds)
    .exec(patron.chat_transcriptData())
    .pause(10 seconds)
    //.exec(patron.patronChatSendText("${chatSessionId}", "Customer" ,"${Text}", "v10.0"))
    .exec(patron.patronChatSendText("${ChatSessionId}", "Customer" ,"${Text}", "v10.0"))
    .pause(10 seconds)
    //.exec(agents.endContact(agents.sessionId, "${contactId}", "v10.0"))
    .exec(agents.endContact(agents.sessionId, agents.contactId, "v10.0"))
    .pause(5 seconds)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "false", "v2.0"))
    .pause(20 seconds)


}