package Configurations

package object Configurations {

  //<editor-fold desc="APIs">
  val plusURL = "InContactAPI/"
  var APICallRecord = "/services/v2.0/agent-sessions/{sessionId}/interactions/{contactId}/record"
  var APINextEvent = "/services/v2.0/agent-sessions/{sessionId}/get-next-event"

  //</editor-fold>

  //<editor-fold desc="General">

  val incontact_header_Token = "aW50ZXJuYWxAaW5Db250YWN0IEluYy46UVVFNVFrTkdSRE0zUWpFME5FUkRSamczUlVORFJVTkRRakU0TlRrek5UYz0="

  def authTokenHeader(): Map[String, String] = {
    Map("Content-Type" -> "application/json", "Authorization" -> "Basic ".concat(this.incontact_header_Token), "Accept" -> "application/json")
  }

  var listOfPhones = Array[(Boolean,String)]((true,"9990370004"),(true, "9990370008"),(true,"9990370010"),(true, "9990370013"))

  //</editor-fold>

}
