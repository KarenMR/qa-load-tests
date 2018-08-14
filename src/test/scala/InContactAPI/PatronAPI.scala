package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.body
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import loadusers.LoadSimulationSetUp
import Configurations.Configurations._
import scala.concurrent.duration._

class PatronAPI{

  val auths = new inContactAuth
  val loadAgents1 = new LoadSimulationSetUp
  var bodyHtml : String = _
  var chatSessionId : String = _

  /**
    * Start Patron Chat
    *
    * @param pointOfContact chat poc
    * @param version        version of the API
    * @return
    */
  def startChat(pointOfContact: String, version: String): ChainBuilder = {
    val chatsUrl = loadAgents1.baseURL.concat(plusURL.concat(APIChats))
    var jsBody = "{\n  \"pointOfContact\": \""+ pointOfContact +"\"\n}"
    exec(
      http("Start Chat")
        //.post(loadAgents1.baseURL.concat("/InContactAPI/services/".concat(version).concat("/contacts/chats")))
        .post(chatsUrl)
        .headers(auths.auth_Token_ob.incontactHeaders())
        .body(StringBody(jsBody)).asJSON
        //.body(StringBody("{\r\n  \"pointOfContact\": \"".concat(pointOfContact).concat("\"\r\n}"))).asJSON
        .check(status.is(202))
        //chatSessionId
        //.check(jsonPath("$..chatSessionId").saveAs("chatSessionId")))
        .check(jsonPath("$.chatSessionId").saveAs("ChatSessionId")))
      /*
      .exec(session => {
        println(body.StringBody("""{"pointOfContact":""".concat(pointOfContact).concat("""}"""))).toString()
        println("${ChatSessionId}").toString()
        session
      })*/
  }

  chatSessionId = "${ChatSessionId}"

  def chat_transcriptData(): ChainBuilder = {
    exec(
      http("Chat Transcript Data")
        .post(APIChatTranscript)
        .headers(auths.auth_Token_ob.incontactHeaders())
        .body(StringBody("{}")).asJSON
        .check(status.is(200))
        .check(jsonPath("$..Messages[1].Text").exists.saveAs("Text"))
        .check(jsonPath("$..TimeStamp").exists.saveAs("TimeStamp")))
      .pause(8)
      /*
      .exec(session => {
        println("33333")
        println("Session Status: " + (session("Text").as[String]))
        println("Time Stamp: " + (session("TimeStamp").as[String]))
        session
      })
      */
  }

  def email_transcriptData(): ChainBuilder = {
    exec(
      http("Email Transcript Data")
        .post(APIEmailTranscript)
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

  bodyHtml =  "${bodyHtml}"

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

  def patronChatSendText(chatSession: String, label: String, message:String,version:String): ChainBuilder={
    var chatSendTextUrl = loadAgents1.baseURL.concat(plusURL.concat(APIChatsSendText).replace("CHATSESSION", chatSession))
    var jsBody = "{\n  \"label\": \""+ label +"\",\n  \"message\": \""+ message +"\"\n}"
    exec(
      http("Send Patron Chat Test")
        //.post(loadAgents1.baseURL.concat("InContactAPI/services/").concat(version).concat("/contacts/chats/").concat(chatSession).concat("/send-text"))
        .post(chatSendTextUrl)
        .headers(auths.auth_Token_ob.incontactHeaders())
        //.body(StringBody("""{label": """.concat(label).concat(""","message":""").concat(message).concat(""""}""""))).asJSON
        //.body(StringBody("""{"label": """".concat(label).concat("""",  "message": """").concat(message).concat(""""}"""))).asJSON
          .body(StringBody(jsBody)).asJSON
        .check(status.is(202))
    )
      .pause(10 seconds)
  }

}
