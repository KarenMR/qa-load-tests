package loadusers

import java.io.File


import io.gatling.core.Predef._
import io.gatling.core.feeder.RecordSeqFeederBuilder




class LoadSimulationSetUp extends Simulation{


  /**
    * Set the Cluster value that you would like to run load on
    * i.e SC1, HC15
    */
  val setCluster = "TO31"

  /**
    * Global Variables for load application to run
    * Do not change variables
     */
  var baseURL:String =_
  var csvFile:String =_
  var phoneSkillId:String=_
  var phoneScriptName:String=_
  var chatPOC:String=_
  var workItemPoc:String=_
  var workItemSkillId:String=_
  var outboundPhoneSkill:String=_
  var emailSkill:String=_
  var emailOBSkillId:String=_

  csvFile = new File("src/test/resources/data/".concat(setCluster).concat("LoadAgents.csv")).getAbsolutePath


    setCluster match {
   case "HC15" =>
     baseURL = "http://hc-c15web01/"
     phoneSkillId = "3034"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "696f61a3-94fb-469d-83cd-4ad157bd7d13"
     workItemPoc ="1560"
     workItemSkillId="3035"
     outboundPhoneSkill="3037"
     emailSkill="3042"
   case "SC1" =>
     baseURL = "https://api-sc1.ucnlabext.com/"
     phoneSkillId = "165861"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "f608ef92-a1e7-41e2-bd3d-570a0cee5778"
     workItemPoc =""
     workItemSkillId=""
     outboundPhoneSkill = "165864"
   case "SC11" =>
     baseURL = "https://api-sc11.ucnlabext.com/"
     phoneScriptName = "SpawnInboundPhone"
     phoneSkillId = "1622"
     workItemPoc = "218417"
     workItemSkillId = "1623"
     outboundPhoneSkill = "1625"
   case "TO31" =>
     baseURL = "https://api-to31.test.nice-incontact.com/"
     phoneSkillId = "306905"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "59df09a3-5d78-4fb0-b70d-96d81cfc9e7c"
     outboundPhoneSkill="306906"
     emailSkill="306908"
     emailOBSkillId = "306907"

   case "SO31" =>
        baseURL ="https://api-so31.staging.nice-incontact.com/InContactAuthorizationServer/"
   case "SO32" =>
     baseURL = "https://api-so32.staging.nice-incontact.com/"
     phoneSkillId = "185200"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "20c69d18-dad6-4bd2-bcc6-c8aba222aa5f"
     outboundPhoneSkill="204242"
     emailSkill="204361"
     emailOBSkillId = "204362"
 }

  val feeder: RecordSeqFeederBuilder[String] = csv(csvFile).circular

}
