package CustomerProfiles
import io.gatling.core.Predef._
import com.chat_scenarios.{QM_InBoundChat, inboundChat}
import email_scenarios.QM_InBoundEmailTest
import io.gatling.core.structure.ScenarioBuilder
import phone_scenarios.{inboundPhone, outboundPhoneScenario}

import scala.concurrent.duration._

class QM_Profile extends Simulation{
  val obPhoneCall =  new outboundPhoneScenario
  //val qm_chat = new QM_InBoundChat
  //val qm_email =  new QM_InBoundEmailTest

  val qmenv : ScenarioBuilder = scenario("QM Analytics")
  //Execute all test at the same time
  /*
  setUp(obPhoneCall.outboundPhoneTest.inject(rampUsers(16) over (20 seconds)),
  qm_chat.ChatTranscriptTest.inject(rampUsers(3) over (10 seconds)),
  qm_email.EmailTranscriptTest.inject(rampUsers(2) over (1 seconds)))
*/

  //Execute one by one (Recommended)
  setUp(obPhoneCall.outboundPhoneTest.inject(rampUsers(1) over (2 seconds)))  //Phone call
  //setUp(qm_chat.ChatLoadTest.inject(rampUsers(5) over (6 seconds))) //Chat
  //setUp(qm_chat.ChatTranscriptTest.inject(rampUsers(10) over (10 seconds))) // Chat Transcript
  //setUp(qm_email.EmailTranscriptTest.inject(rampUsers(10) over (1 seconds))) //Email Transcript

}
