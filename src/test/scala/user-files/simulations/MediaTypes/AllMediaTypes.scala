package test

import email_scenarios.outboundEmail
import io.gatling.core.Predef._
import phone_scenarios.inboundPhone
import voicemail_scenarios.voicemail

import scala.concurrent.duration._


class AllMediaTypes extends Simulation{
  val phoneCallTest = new inboundPhone
  val voiceMail = new voicemail
  var outboundEmail = new outboundEmail



  setUp(outboundEmail.OutboundEmailTest.inject(constantUsersPerSec(300) during(30 minute)))//,outboundEmail.OutboundEmailTest.inject(rampUsers(200) over(10 minute)))



}
