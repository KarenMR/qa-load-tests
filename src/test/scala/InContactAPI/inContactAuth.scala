package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp
import Configurations.Configurations._

class inContactAuth{

  val loadSim = new LoadSimulationSetUp

  object auth_Token_ob {

    val accessToken: String = "${Access}"

    def Auth(userName: String, Password: String): ChainBuilder = {
      exec(http("Get Auth Token")
        .post(loadSim.baseURL.concat("InContactAuthorizationServer/Token"))
        .headers(authTokenHeader())
        .body(StringBody("{\n  \"grant_type\": \"password\",\n  \"userName\": \"".concat(userName).concat("\",\n  \"password\": \"").concat(Password).concat("\",\n  \"scope\": \"RealTimeApi ChatApi CustomApi AdminApi AgentApi ReportingApi AuthenticationApi\"\n}"))).asJSON
        .check(status.is(200)).check(jsonPath("$.access_token").exists.saveAs("Access"))
      )
    }

    def incontactHeaders(): Map[String, String] = {
      Map("Authorization" -> "Bearer ".concat(accessToken))
    }

  }


}
