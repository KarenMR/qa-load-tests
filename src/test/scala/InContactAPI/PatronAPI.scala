package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.body
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp

class PatronAPI{

  val auths = new inContactAuth
  val loadAgents1 = new LoadSimulationSetUp


  /**
    * Start Patron Chat
    *
    * @param pointOfContact chat poc
    * @param version        version of the API
    * @return
    */
  def startChat(pointOfContact: String, version: String): ChainBuilder = {
    exec(
      http("Start Chat")
        .post(loadAgents1.baseURL.concat("/InContactAPI/services/".concat(version).concat("/contacts/chats")))
        .headers(auths.auth_Token_ob.incontactHeaders())
        //.body(StringBody("""{"pointOfContact":""".concat(pointOfContact).concat("""}"""))).asJSON
        .body(StringBody("{\r\n  \"pointOfContact\": \"".concat(pointOfContact).concat("\"\r\n}"))).asJSON
        .check(status.is(202)))
      .exec(session => {
        println(body.StringBody("""{"pointOfContact":""".concat(pointOfContact).concat("""}""")))
        session
      })
  }

  def createWorkItem(pointOfContact: String, workItemId: String, workItemPayLoad: String, workItemType: String, from: String, version: String): ChainBuilder = {
    exec(session => {
      println("""{"pointOfContact": """".concat(pointOfContact).concat("""", "workItemID": """").concat(workItemId).concat("""","workItemPayload": """").concat(workItemPayLoad).concat("""", "workItemType": """").concat(workItemType).concat("""", "from" : """").concat(from).concat(""""}"""))
      session
    })
    .exec(
      http("Create WorkItem")
        .post(loadAgents1.baseURL.concat("/InContactAPI/services/".concat(version).concat("/interactions/work-items")))
        .headers(auths.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"pointOfContact": """".concat(pointOfContact).concat("""", "workItemID": """").concat(workItemId).concat("""","workItemPayload": """").concat(workItemPayLoad).concat("""", "workItemType": """").concat(workItemType).concat("""", "from" : """").concat(from).concat(""""}""") ) ).asJSON
      .check(status.is(202))
    )


  }

}
