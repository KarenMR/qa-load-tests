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

  def chat_transcriptData(): ChainBuilder = {
    exec(
      http("Chat Transcript Data")
        .post("http://analytics.test.nice-incontact.com:8001/InContactAPI/services/v9.0/contacts/1/chat-transcript")
        .headers(auths.auth_Token_ob.incontactHeaders())
        .body(StringBody("{}")).asJSON
        .check(status.is(200))
        .check(jsonPath("$..Text").exists.saveAs("Text"))
        .check(jsonPath("$..TimeStamp").exists.saveAs("TimeStamp")))
      .pause(15)
      .exec(session => {
        println("33333")
        println("Session Status: " + (session("Text").as[String]))
        println("Time Stamp: " + (session("TimeStamp").as[String]))
        session
      })
  }

  def email_transcriptData(): ChainBuilder = {
    exec(
      http("Email Transcript Data")
        .post("http://analytics.test.nice-incontact.com:8001/InContactAPI/services/v9.0/contacts/1/email-transcript")
        .headers(auths.auth_Token_ob.incontactHeaders())
        .body(StringBody("{}")).asJSON
        .check(status.is(200))
        .check(jsonPath("$..emailTypeId").exists.saveAs("emailTypeId"))
        .check(jsonPath("$..sentDate").exists.saveAs("sentDate"))
        .check(jsonPath("$..fromAddress").exists.saveAs("fromAddress"))
        .check(jsonPath("$..toAddress").exists.saveAs("toAddress"))
        .check(jsonPath("$..subject").exists.saveAs("subject"))
        .check(jsonPath("$..bodyHtml").exists.saveAs("bodyHtml")))
      .pause(15)
      .exec(session => {
        println("44444")
        println("Email TypeId: " + (session("emailTypeId").as[String]))
        println("Sent Date: " + (session("sentDate").as[String]))
        println("From Address: " + (session("fromAddress").as[String]))
        println("To Address: " + (session("toAddress").as[String]))
        println("Subject: " + (session("subject").as[String]))
        println("Body Html: " + (session("bodyHtml").as[String]))
        session
      }).pause(10)
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
