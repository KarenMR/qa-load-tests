package CustomerProfiles

import InContactAPI.AdminAPI
import io.gatling.core.Predef._
import phone_scenarios.OutboundPhone
import workItem_scenarios.WorkItemTest
import InContactAPI.inContactAuth
import com.MediaTypes.APITest
import io.gatling.core.structure.ScenarioBuilder
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._
import scala.language.postfixOps

class LLSProfile extends Simulation {

  val workItem = new WorkItemTest
  val phoneCall = new OutboundPhone
  val admin = new AdminAPI
  val authToken = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  val loginTest = new APITest

  val llsWebServiceCall: ScenarioBuilder = scenario("WebService Call")
    //.feed(loadAgents.feeder)
    //.exec(authToken.auth_Token_ob.Auth("${UserName}", "${Password}"))
    //.exec(admin.getScript("ThrottleScript1", "v4.0"))
    //.exec(admin.spwanScript(admin.scriptId,"1622","","","v4.0"))
  setUp(loginTest.changeState.inject(rampUsers(5000) over (1 minute)))//phoneCall.outboundPhoneTest.inject(rampUsers(600) over (60 seconds)))

  val concurrentIbCallswithConferenceCalls: ScenarioBuilder = scenario("4,200 Agent with 4,000 Concurrent IB call with Conference")
  setUp()

}
