package Configurations

package object Configurations {
  val incontact_header_Token = "aW50ZXJuYWxAaW5Db250YWN0IEluYy46UVVFNVFrTkdSRE0zUWpFME5FUkRSamczUlVORFJVTkRRakU0TlRrek5UYz0="

  def authTokenHeader(): Map[String, String] = {
    Map("Content-Type" -> "application/json", "Authorization" -> "Basic ".concat(this.incontact_header_Token), "Accept" -> "application/json")
  }

  var listOfPhones = Array[(Boolean,String)]((true,"9990370004"),(true, "9990370008"),(true,"9990370010"),(true, "9990370013"))

}
