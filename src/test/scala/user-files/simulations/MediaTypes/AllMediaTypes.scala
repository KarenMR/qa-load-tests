package test

import email_scenarios.OutboundEmail
import io.gatling.core.Predef._
import phone_scenarios.PhoneCallTest
import voicemail_scenarios.VoiceMail

import scala.concurrent.duration._


class AllMediaTypes extends Simulation{
  val phoneCallTest = new PhoneCallTest
  val voiceMail = new VoiceMail
  var outboundEmail = new OutboundEmail



  setUp(phoneCallTest.inboundPhoneLoadTest.inject(rampUsers(20) over(20 seconds)),voiceMail.VoiceMailLoadTest.inject(rampUsers(50) over(20 seconds)))



}
