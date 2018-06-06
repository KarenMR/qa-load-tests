package LoadProfiles

import InContactAPI.AdminAPI
import io.gatling.core.Predef._
import phone_scenarios.{inboundPhone, outboundPhone}
import workItem_scenarios.workItem
import InContactAPI.inContactAuth
import com.MediaTypes.APITest
import com.chat_scenarios.inboundChat
import email_scenarios.outboundEmail
import io.gatling.core.structure.ScenarioBuilder
import loadusers.{LoadSimulationSetUp, LogOutAgents, LoginAgents}
import voicemail_scenarios.voicemail

import scala.concurrent.duration._
import scala.language.postfixOps

class SmallLoadProfile extends Simulation {
  val phoneCallTest = new inboundPhone
  val voiceMail = new voicemail
  var outboundEmail = new outboundEmail
  var inboundChat = new inboundChat
  val loginAgent = new LoginAgents
  val logoutAgent = new LogOutAgents

  setUp(phoneCallTest.inboundPhoneLoadTest.inject(rampUsers(10) over (10 seconds)),
    voiceMail.VoiceMailLoadTest.inject(rampUsers(10) over (10 seconds)),
    outboundEmail.OutboundEmailTest.inject(rampUsers(10) over (10 seconds)),
    inboundChat.ChatLoadTest.inject(rampUsers(10) over (10 seconds)))
}
