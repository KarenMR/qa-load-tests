package test

import InContactAPI.AdminAPI
import io.gatling.core.Predef._
import phone_scenarios.PhoneCallTest
import workItem_scenarios.WorkItemTest
import InContactAPI.inContactAuth
import loadusers.LoadSimulationSetUp

class LLSProfile extends Simulation {

  val workItem = new WorkItemTest
  val phoneCall = new PhoneCallTest
  val admin = new AdminAPI
  val authToken = new inContactAuth
  val loadAgents = new LoadSimulationSetUp

  val llsWebServiceCall = scenario("WebService Call")
    .feed(loadAgents.feeder)
    .exec(authToken.auth_Token_ob.Auth("${UserName}", "${Password}"))
    .exec(admin.getScript("ThrottleScript1", "v4.0"))
    .exec(admin.spwanScript(admin.scriptId,"1622","","","v4.0"))
  setUp(llsWebServiceCall.inject(atOnceUsers(1)), workItem.WorkItemLoadTest.inject(atOnceUsers(10)), phoneCall.inboundPhoneLoadTest.inject(atOnceUsers(0)))

}
