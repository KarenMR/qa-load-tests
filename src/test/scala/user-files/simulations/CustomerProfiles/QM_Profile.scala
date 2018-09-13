package CustomerProfiles
import io.gatling.core.Predef._
import com.chat_scenarios.{QM_InBoundChat, inboundChat}
import email_scenarios.QM_InBoundEmailTest
import email_scenarios.obEmailScenario
import io.gatling.core.structure.ScenarioBuilder
import phone_scenarios.{inboundPhone, outboundPhoneScenario}
import email_scenarios.outboundEmail

import scala.concurrent.duration._

class QM_Profile extends Simulation{
  val obPhoneCall =  new outboundPhoneScenario
  //val qm_chat = new QM_InBoundChat
  //val obEmail = new obEmailScenario
  //val qm_email =  new QM_InBoundEmailTest

  val qmenv : ScenarioBuilder = scenario("QM Analytics")

  //<editor-fold desc = "Execute one by one (Recommended)">

  setUp(obPhoneCall.outboundPhoneTest.inject(rampUsers(200) over (200 seconds)))  //Phone call
  //setUp(qm_chat.QmChatLoadTest.inject(rampUsers(200) over (200 seconds))) //Chat
  //setUp(obEmail.outboundEmailTest.inject(rampUsers(200)over(200 seconds))) //Email

  //</editor-fold>

  //<editor-fold desc = "All test at the same time">

  /*
    setUp(obPhoneCall.outboundPhoneTest.inject(rampUsers(16) over (20 seconds)),
    qm_chat.QmChatLoadTest.inject(rampUsers(3) over (10 seconds)),
    qm_email.EmailTranscriptTest.inject(rampUsers(2) over (1 seconds)),
    obEmail.outboundEmailTest.inject(rampUsers(10)over(10 seconds)))
  */

  //</editor-fold>
}
