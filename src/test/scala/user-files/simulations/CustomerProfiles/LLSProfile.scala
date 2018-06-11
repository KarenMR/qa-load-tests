package CustomerProfiles

import InContactAPI.AdminAPI
import io.gatling.core.Predef._
import phone_scenarios.inboundPhone
import phone_scenarios.inboundPhoneConferenceCalls
import workItem_scenarios.workItem
import InContactAPI.inContactAuth
import com.MediaTypes.APITest
import io.gatling.core.structure.ScenarioBuilder
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._
import scala.language.postfixOps

class LLSProfile extends Simulation {

  val workItem = new workItem
  val phoneCall = new inboundPhone
  val phoneCalls = new inboundPhoneConferenceCalls
  val admin = new AdminAPI
  val authToken = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  val loginTest = new APITest

  val nothing:ScenarioBuilder = scenario("Do Nothing")
      .pause(2 seconds)

  val llsWebServiceCall: ScenarioBuilder = scenario("WebService Call")
  setUp(phoneCall.inboundPhoneLoadTest.inject(rampUsers(75) over (1 minutes)), phoneCalls.inboundPhoneConferenceCallLoadTest.inject(rampUsers(25) over (30 seconds)))//phoneCall.outboundPhoneTest.inject(rampUsers(600) over (60 seconds)))



}
