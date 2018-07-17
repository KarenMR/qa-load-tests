package LoadProfiles

import email_scenarios.{inboundEmail, outboundEmail}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import phone_scenarios.{inboundPhone, outboundPhoneScenario}
import voicemail_scenarios.voicemail
import workItem_scenarios.workItem
import scala.concurrent.duration._

class ExtraLargeLoadProfile extends Simulation {
  val outboundPhoneCall = new outboundPhoneScenario
  val inboundPhoneCall = new inboundPhone
  val workItem = new workItem
  val voiceMail = new voicemail
  val inboundEmail = new inboundEmail
  val outboundEmail = new outboundEmail
  val extraLargeLoad: ScenarioBuilder = scenario("Extra Large Load Profile")
  setUp(inboundPhoneCall.inboundPhoneLoadTest.inject(rampUsers(4200) over (4 minutes)), outboundPhoneCall.outboundPhoneTest.inject(rampUsers(4200) over (4 minutes))
    , workItem.WorkItemLoadTest.inject(rampUsers(4200) over (4 minutes)), outboundEmail.OutboundEmailTest.inject(rampUsers(4200) over (4 minutes)), inboundEmail.InboundEmail.inject(rampUsers(4200) over (4 minutes)))

}