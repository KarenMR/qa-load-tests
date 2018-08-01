package Configurations

package object Configurations {

  //<editor-fold desc="APIs">
  val plusURL = "InContactAPI"
  var APICallRecord = "/services/v2.0/agent-sessions/SESSIONID/interactions/CONTACTID/record"
  var APINextEvent = "/services/v2.0/agent-sessions/SESSIONID/get-next-event"
  var APIEmailOutbound = "/services/v4.0/agent-sessions/SESSIONID/interactions/email-outbound"
  var APIEmailSend = "/services/v4.0/agent-sessions/SESSIONID/interactions/CONTACTID/email-send"

  //</editor-fold>

  //<editor-fold desc="General">

  val toAddress = "test@niceincontact.com"
  val fromAddress = "test@test.com"
  val subject = "Test Subject from {cluster}"
  val boddy = "This is a test Boddy from {cluster}"

  val incontact_header_Token = "aW50ZXJuYWxAaW5Db250YWN0IEluYy46UVVFNVFrTkdSRE0zUWpFME5FUkRSamczUlVORFJVTkRRakU0TlRrek5UYz0="

  def authTokenHeader(): Map[String, String] = {
    Map("Content-Type" -> "application/json", "Authorization" -> "Basic ".concat(this.incontact_header_Token), "Accept" -> "application/json")
  }

  var listOfPhones = Array[(Boolean,String)]((true,"9990370004"),(true, "9990370008"),(true,"9990370010"),(true, "9990370013"))

  //</editor-fold>

  //<editor-fold desc = "Bodies">

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
