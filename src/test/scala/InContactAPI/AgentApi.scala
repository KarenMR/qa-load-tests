package InContactAPI

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import loadusers.LoadSimulationSetUp
import scala.collection.mutable.ArrayBuffer
import Configurations.Helpers
import Configurations.Configurations._

class AgentApi {

  val auther = new inContactAuth
  val loadAgents = new LoadSimulationSetUp
  var sessionId: String = _
  var contactId: String = _
  var listOfSessionIDs = new ArrayBuffer[String](5)
  var listOfSessionIDsNew = new Array[String](2)
  var helpers = new Helpers

  /**
    * Launches Agents session
    *
    * @param agentPhone agents phone number
    * @param version    version of the API
    * @return sessionID
    */
  def getAgentSession(agentPhone: String, version: String): ChainBuilder = {
    val url = loadAgents.baseURL.concat("InContactAPI/services/".concat("v2.0").concat("/agent-sessions"))
    var phone2 = agentPhone
    exec(
      http("Get Session")
        .post(url)
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("{\n  \"stationId\": \"\",\n  \"stationPhoneNumber\": \"".concat(agentPhone).concat("\",\n  \"inactivityTimeout\": \"\",\n  \"asAgentId\": \"\"\n}")))
        .check(status.is(202)).check(jsonPath("$.sessionId").exists.saveAs("sessionId")))
      .exec(session => {
        sessionId = session("sessionId").as[String]
        listOfSessionIDs += sessionId
        session
      })
      .exec(session => {
      println("These are Session IDs")
      listOfSessionIDs.foreach(x => println(x))
      session
    })
  }

  sessionId = "${sessionId}"

  def getContactId(): ChainBuilder ={
    val url = loadAgents.baseURL.concat(plusURL.concat(APINextEvent.replace("{sessionId}", sessionId)))
    exec(
      http("Get ContactId")
        .get(url)
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("{\n  \"timeout\": \"10\"\n}")).asJSON
        .check(status.is(200)).check(jsonPath("$.events[1].ContactID").exists.saveAs("ContactID")))
      exec(session=>{
        println("654987")
        contactId = session("ContactID").as[String]
        println(contactId)
        session})
  }

  contactId = "${ContactID}"

  /**
    * End Agent Session
    *
    * @param sessionId         session id of the agent
    * @param forceLogOff       true or false
    * @param endContact        true or false
    * @param ignorePersonQueue ture or false
    * @param version           version of api
    * @return
    */
  def endAgentSession(sessionId: String, forceLogOff: String, endContact: String, ignorePersonQueue: String, version: String): ChainBuilder = {
    exec(
      http("End Agent Session")
        .delete(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId)))
        .headers(auther.auth_Token_ob.incontactHeaders())
        //.body(StringBody("""{"forceLogoff":""".concat(forceLogOff).Concat(","endContacts":").concat("false","ignorePersonalQueue": "true"}""")).asJSON
        .body(StringBody(
        """{
            "forceLogoff":""".concat(forceLogOff).concat(
          """,
            "endContacts":""").concat(endContact).concat(
          """,
             "ignorePersonalQueue":""").concat(ignorePersonQueue).concat("""}"""))).asJSON
        .check(status.is(202)))
     // .exec(session => {
      //  println(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId)).toString)
     //   session
     // })
  }

  /**
    * Sets Agent State
    *
    * @param state   Available or Unavailable
    * @param reason  reason for the state change
    * @param version version of the Api
    * @return status code
    */
  def changeAgentState(state: String, reason: String, version: String): ChainBuilder = {
  //  exec(session => {
    //  println("{\r\n  \"state\": \"".concat(state).concat("\",\r\n  \"reason\": \"").concat(reason).concat("\"\r\n}"))
     // session
    //})
      exec(
      http("Change Agent State")
        .post(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/".concat(sessionId).concat("/state"))))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("{\r\n  \"state\": \"".concat(state).concat("\",\r\n  \"reason\": \"").concat(reason).concat("\"\r\n}"))).asJSON
        .check(status.is(202))
    )

  }
  /**
    * Dials Agent Leg
    *
    * @param sessionId session id of the agent
    * @param skillId skill id to dial ob contact
    * @param version version of the api
    * @return
    */
  def dialAgentPhone(sessionId: String, skillId: String, version: String): ChainBuilder = {
    var phoneNumber = helpers.getPhone().toString()

    exec(
      http("Dial Phone")
        .post(loadAgents.baseURL.concat("InContactAPI/services/").concat(version).concat("/agent-sessions/").concat("${sessionId}").concat("/dial-phone"))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"phoneNumber": """.concat(phoneNumber).concat(""","skillId": """.concat(skillId).concat("""}""")))).asJSON
        .check(status.is(202)))
  }

  def callRecord(): ChainBuilder = {
    var url = loadAgents.baseURL.concat(plusURL.concat(APICallRecord.replace("{sessionId}", sessionId)).replace("{contactId}", contactId))
    exec(
      http("Call Record")
        .post("")
        .headers(auther.auth_Token_ob.incontactHeaders())
        .check(status.is(200))
    )


  }


  /**
    * end agent leg
    *
    * @param sessionId session id of the agent
    * @param version   version of the api
    * @return
    */
  def endAgentLeg(sessionId: String, version: String): ChainBuilder = {
    exec(
      http("End Agent Leg")
        .post(loadAgents.baseURL.concat("InContactAPI/services/v2.0/agent-sessions/".concat(sessionId).concat("/agent-phone/end")))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{}""")).asJSON
        .check(status.is(202)))
     .exec(session => {
      println(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId)))
       session
     })

  }

  /**
    * Accepts Contacts
    *
    * @param sessionId sessiond id of the agent
    * @param contactId contact id the agent is handling
    * @param version   version of the api
    * @return
    */
  def acceptContact(sessionId: String, contactId: String, version: String): ChainBuilder = {
    exec(
      http("Accept Contact")
        .post(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/".concat(contactId).concat("/accept"))))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{}""")).asJSON
        .check(status.is(202)))
  }

  /**
    * end Contact Api
    *
    * @param sessionId session Id of the Agent
    * @param contactId Contact id the Agent is handling
    * @param version   the version of the API
    * @return
    */
  def endContact(sessionId: String, contactId: String, version: String): ChainBuilder = {
    exec(
      http("End Chat")
        .post(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/".concat(contactId).concat("/end"))))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{}""")).asJSON
        .check(status.is(202)))
  }

  /**
    * Create Email API
    *
    * @param sessionId session id of the Agent
    * @param skillId   Skill ID that Agent wants to create an outbound email on
    * @param toAddress Email address to end the email to
    * @param version   version of the api
    * @return
    */
  def createEmail(sessionId: String, skillId: String, toAddress: String, version: String): ChainBuilder = {
    exec(
      http("Create Outbound Email")
        .post(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/email-outbound")))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("""{"skillId": """.concat(skillId).concat(""", "toAddress": """.concat(toAddress).concat(""" "}""")))).asJSON
        .check(status.is(202))
    )
  }

  /**
    * Send Email API
    *
    * @param sessionId       session id of the agent
    * @param contactId       contact id of the contact
    * @param skillId         skillid for email
    * @param toAddress       email address to send to the agent
    * @param fromAddress     email address coming from
    * @param ccAddress       cc emails
    * @param bccAddress      bcc emails
    * @param subject         email subject
    * @param bodyHtml        email body
    * @param attachements    email attachements
    * @param attacmentsNames email attachmentNames
    * @param version         version of the API
    * @return
    */
  def sendEmail(sessionId: String, contactId: String, skillId: String, toAddress: String, fromAddress: String, ccAddress: String, bccAddress: String, subject: String,
                bodyHtml: String, attachements: String, attacmentsNames: String, version: String): ChainBuilder = {
    exec(
      http("Send Email")
        .post(loadAgents.baseURL.concat("/InContactAPI/services/".concat(version).concat("/agent-sessions/").concat("${sessionId}").concat("/interactions/").concat("${contactId}").concat("/email-send")))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody(
          """{
          "skillId": """.concat(skillId).concat(
            """,
          "toAddress": """).concat(toAddress).concat(
            """,
          "fromAddress": """).concat(fromAddress).concat(
            """,
          "ccAddress": """).concat(ccAddress).concat(
            """,
          "bccAddress": """).concat(
            """",
          "subject": """).concat(subject).concat(
            """,
          "bodyHtml": """).concat(bodyHtml).concat(
            """ ",
          "attachments": """).concat(attachements).concat(
            """,
          "attachmentNames": """).concat(attacmentsNames).concat(""""}"""))).asJSON
        .check(status.is(202))
    )

  }

  /**
    * Conference Call Api
    * @param sessionId session id of the active agent
    * @param version Api version
    * @return
    */
  def conferenceCall(sessionId: String, version: String): ChainBuilder ={
    exec(
      http("Conference Call")
        .post(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/conference-calls")))
            .headers(auther.auth_Token_ob.incontactHeaders())
            .body(StringBody("{}"))
        .check(status.is(202)))
        .exec(session => {
          println(loadAgents.baseURL.concat("InContactAPI/services/".concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/conference-calls")))
          session
        }
    )

  }
  /**
    * Hold Contact API
    * @param sessionId Session Id of the Agent
    * @param contactId Active Contacts id
    * @param version Version of the API
    * @return
    */
  def holdContact (sessionId: String, contactId: String, version: String): ChainBuilder ={
    exec(
      http("Hold Contact")
        .post(loadAgents.baseURL.concat("InContactAPI/services/").concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/".concat(contactId).concat("/hold")))
        .headers(auther.auth_Token_ob.incontactHeaders())
        .body(StringBody("{}"))
        .check(status.is(202)))
        .exec(session => {
          println(loadAgents.baseURL.concat("InContactAPI/services/").concat(version).concat("/agent-sessions/").concat(sessionId).concat("/interactions/".concat(contactId).concat("/hold")))
          session
        }
    )
  }





}
