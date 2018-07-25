package email_scenarios

import InContactAPI.{inContactAuth, AgentApi, EmailSMPT, PatronAPI}
import scala.concurrent.duration._
import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp
import Configurations.Configurations._

class QM_InBoundEmailTest extends Simulation {
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val auth = new inContactAuth
  val token1 = incontact_header_Token
  val patron = new PatronAPI
  /*
  val sendEmail = new EmailSMPT("1IBemail_masterBU@incontactemail.com", "", "", "test.test@test.com", "hello", "Test", "HC-C15COR01")
  sendEmail.sendMessage
  val baseUrl =  loadAgents.baseURL
  val joinSessionAPIUrl = baseUrl.concat("InContactAPI/".concat("/services/v2.0/agent-sessions/join"))
  val emailOutBoundAPIUrl = baseUrl.concat("InContactAPI/".concat("services/v4.0/agent-sessions/{sessionId}/interactions/email-outbound"))
  var emailUrl = ""
  var sessionId = ""
*/

  val EmailTranscriptTest = scenario("InboundEmail Load")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v2.0"))
    .pause(10 seconds)
    .exec(patron.email_transcriptData())
    .pause(10 seconds)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "false", "v2.0"))
  //setUp(InboundEmail.inject(rampUsers(10) over (5 minute)))

}
