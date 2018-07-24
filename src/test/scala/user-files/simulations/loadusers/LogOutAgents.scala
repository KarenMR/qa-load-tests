package loadusers

import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
//import io.gatling.http.Predef._
//import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


class LogOutAgents extends Simulation {

  val auth = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val patron = new PatronAPI

  val logOutAgents = scenario("Log Out Agents")
    .pause(20)
    .exec(agents.endAgentSession(agents.sessionId, "false", "false", "false", "v2.0"))
}
