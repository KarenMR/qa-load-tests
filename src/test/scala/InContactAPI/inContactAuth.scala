package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp

class inContactAuth{

  val incontact_header_Token = "aW50ZXJuYWxAaW5Db250YWN0IEluYy46UVVFNVFrTkdSRE0zUWpFME5FUkRSamczUlVORFJVTkRRakU0TlRrek5UYz0="
  //val incontact_header_Token ="QUE5QkNGRDM3QjE0NERDRjg3RUNDRUNDQjE4NTkzNTc="
  val loadSim = new LoadSimulationSetUp

  def authTokenHeader(): Map[String, String] = {
    Map("Content-Type" -> "application/json", "Authorization" -> "Basic ".concat(incontact_header_Token), "Accept" -> "application/json")
  }

  object auth_Token_ob {

    val accessToken: String = "${Access}"

    def Auth(userName: String, Password: String): ChainBuilder = {
      exec(http("Get Auth Token")
        .post(loadSim.baseURL.concat("InContactAuthorizationServer/Token"))
        .headers(authTokenHeader())
        .body(StringBody("{\n  \"grant_type\": \"password\",\n  \"userName\": \"".concat(userName).concat("\",\n  \"password\": \"").concat(Password).concat("\",\n  \"scope\": \"RealTimeApi ChatApi CustomApi AdminApi AgentApi ReportingApi AuthenticationApi\"\n}"))).asJSON
        .check(status.is(200)).check(jsonPath("$.access_token").exists.saveAs("Access"))
      )
       /* .exec(session => {
          println()
          println(Password)
          println(loadSim.baseURL.concat("InContactAuthorizationServer/Token"))
          session
        })*/
    }

    def incontactHeaders(): Map[String, String] = {
      Map("Authorization" -> "Bearer ".concat(accessToken))
    }

    //val authHeader = Map("Authorization" -> "Bearer ".concat(accessToken))
  }


}
