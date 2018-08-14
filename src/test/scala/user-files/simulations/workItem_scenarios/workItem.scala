package workItem_scenarios

//import InContactAPI.{AdminAPI, AgentApi, Authentication, PatronAPI}
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class workItem extends Simulation {

  val auth = new InContactAPI.inContactAuth
  val agents = new InContactAPI.AgentApi
  val admin = new InContactAPI.AdminAPI
  val patron = new InContactAPI.PatronAPI
  val loadagent = new loadusers.LoadSimulationSetUp

  val WorkItemLoadTest = scenario("WorkItem Test")
    .feed(loadagent.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v6.0"))
    .pause(4)
    .exec(agents.changeAgentState("Available","","v2.0"))
    .exec(patron.createWorkItem(loadagent.workItemPoc, "888", "", "","","v4.0"))
    .pause(8)
    .exec(
    http("Get Next Events")
      .get(loadagent.baseURL.concat("/InContactAPI/services/v2.0/agent-sessions/".concat("${sessionId}").concat("/get-next-event")))
      .headers(auth.auth_Token_ob.incontactHeaders())
      .body(StringBody("""{"timeout": "15"}""")).asJSON
      .check(jsonPath("$.events[?(@.ContactID)].ContactID").saveAs("contactId"))//.check(jsonPath("$.events[?(@.ContactID)].ContactID").exists.saveAs("contactId"))
    )
    .doIf(session => session("contactId").asOption[String].isEmpty)
    {
        exec(
        http("Other Next Events")
        .get(loadagent.baseURL.concat("/InContactAPI/services/v2.0/agent-sessions/".concat("${sessionId}").concat("/get-next-event")))
        .headers(auth.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"timeout": "15"}""")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[?(@.ContactID)].ContactID").exists.saveAs("contactId"))
        )
}
    .exec(session => {
          println("Get Next Events")
          println(session("contactId").as[String])
          session
        })
   .exec(agents.acceptContact(agents.sessionId, "${contactId}"))
   .pause(20)
   .exec(agents.endContact(agents.sessionId, "${contactId}", "v2.0"))
   .pause(4)
   .exec(agents.changeAgentState("Unavailable", "", "v2.0"))
   .pause(10)
   .exec(agents.endAgentSession(agents.sessionId,"false","false","false", "v2.0"))
}