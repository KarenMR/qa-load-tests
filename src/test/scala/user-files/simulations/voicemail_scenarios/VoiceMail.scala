package voicemail_scenarios

import InContactAPI.{AdminAPI, inContactAuth, AgentApi}
import io.gatling.core.Predef._
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


class voicemail extends Simulation {
  val auths = new inContactAuth
  val agents = new AgentApi
  val adminuser = new AdminAPI
  val loadUsers = new LoadSimulationSetUp

  val VoiceMailLoadTest = scenario("VoiceMail Load")
    .feed(loadUsers.feeder)
    .exec(auths.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${PhoneNumber}", "v6.0"))
    .pause(4)
    .exec(agents.changeAgentState("Available", "", "v4.0"))
    .exec(adminuser.getScript("API_SpawnVM", "v4.0" ))
    .exec(adminuser.spwanScript(adminuser.scriptId, "1099", "","", "v4.0"))
    .pause(60)
    .exec(agents.endAgentLeg(agents.sessionId, "v2.0"))
    .pause(10)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "true", "v2.0"))
  setUp(VoiceMailLoadTest.inject(rampUsers(50) over(1 minute)))

}
