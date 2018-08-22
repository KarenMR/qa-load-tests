package Configurations

package object Configurations {

  //<editor-fold desc="APIs">

  val plusURL = "inContactAPI"
  var APICallRecord = "/services/v2.0/agent-sessions/SESSIONID/interactions/CONTACTID/record"
  var APINextEvent = "/services/v2.0/agent-sessions/SESSIONID/get-next-event"
  var APIEmailOutbound = "/services/v4.0/agent-sessions/SESSIONID/interactions/email-outbound"
  var APIEmailSend = "/services/v4.0/agent-sessions/SESSIONID/interactions/CONTACTID/email-send"
  var APIEmailTranscript = "http://analytics.test.nice-incontact.com:8001/InContactAPI/services/v9.0/contacts/1/email-transcript"
  var APIChatTranscript = "http://analytics.test.nice-incontact.com:8001/InContactAPI/services/v9.0/contacts/1/chat-transcript"
  var APIAcceptContact = "/services/v10.0/agent-sessions/SESSIONID/interactions/CONTACTID/accept"
  var APIChats = "/services/v9.0/contacts/chats"
  var APIChatsSendText = "/services/v10.0/contacts/chats/CHATSESSION/send-text"

  //</editor-fold>

  //<editor-fold desc="General">

  val toAddress = "test@test.com"
  val fromAddress = "dontreply@barak.com"
  val subject = "Test Subject from CLUSTER"

  val incontact_header_Token = "aW50ZXJuYWxAaW5Db250YWN0IEluYy46UVVFNVFrTkdSRE0zUWpFME5FUkRSamczUlVORFJVTkRRakU0TlRrek5UYz0="

  def authTokenHeader(): Map[String, String] = {
    Map("Content-Type" -> "application/json", "Authorization" -> "Basic ".concat(this.incontact_header_Token), "Accept" -> "application/json")
  }

  var listOfPhones = Array[(Boolean,String)]((true,"9990370004"),(true, "9990370008"),(true,"9990370010"),(true, "9990370013"))
  //var listOfPhones = Array[(Boolean,String)]((true,"8017155491"),(true, "8017155440"),(true,"9990370010"),(true, "9990370013"))

  //</editor-fold>

  //<editor-fold desc = "Bodies">

  val boddy = "This is a test Body Message: MESSAGE"

  val boddySendEmail = "{\n  \"skillId\": \"SKILLID\",\n  " +
    "\"toAddress\": \"" + toAddress + "\",\n  " +
    "\"fromAddress\": \"" + fromAddress + "\",\n  " +
    "\"ccAddress\": \"\",\n  " +
    "\"bccAddress\": \"\",\n  " +
    "\"subject\": \"" + subject + "\",\n  " +
    "\"bodyHtml\": \"" + boddy + "\",\n  " +
    "\"attachments\": \"\",\n  " +
    "\"attachmentNames\": \"\"\n}"

  //</editor-fold>

}
