package loadusers

import InContactAPI.{AgentApi, inContactAuth, PatronAPI}
import io.gatling.core.Predef._
//import io.gatling.http.Predef._
//import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._


class LoginAgents extends Simulation {

  val auth = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  val agents = new AgentApi
  val patron = new PatronAPI

  val LoginAgents = scenario("Agent Login")
    .feed(loadAgents.feeder)
    .exec(auth.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(agents.getAgentSession("${AgentPhone}", "v2.0"))
    .pause(10 seconds)
}
