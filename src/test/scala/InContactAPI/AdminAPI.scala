package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp

class AdminAPI{

  val loadAgents = new LoadSimulationSetUp
  val authr = new inContactAuth
  var scriptId: String = _


  def getScript(scriptName: String, version: String): ChainBuilder = {
    exec(
      http("Get Script")
        .get(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/scripts")))
        .headers(authr.auth_Token_ob.incontactHeaders())
        .body(StringBody("{\r\n  \"scriptName\": \"".concat(scriptName).concat("\"\r\n}"))).asJSON
        .check(status.is(200)).check(jsonPath("$..scriptId").exists.saveAs("scriptId"))
    )

  }

  scriptId = "${scriptId}"

  def spwanScript(scriptId: String, skillId: String, paramaters: String, startDate: String, version: String): ChainBuilder = {
      exec(session =>
        {
          print("""{"skillId":"""".concat(skillId).concat("""","parameters":""").concat(paramaters).concat(""" "","startDate":"""").concat(startDate).concat(""""}"""))

          session
        })
      .exec(http("Spawn Script")
      .post(loadAgents.baseURL.concat("InContactAPI/services/v4.0/scripts/".concat("${scriptId}").concat("/start")))
      .headers(authr.auth_Token_ob.incontactHeaders())
      .body(StringBody("""{"skillId":"""".concat(skillId).concat("""","parameters":""").concat(paramaters).concat(""" "","startDate":"""").concat(startDate).concat(""""}"""))).asJSON
      .check(status.is(200))
    )
  }


}
