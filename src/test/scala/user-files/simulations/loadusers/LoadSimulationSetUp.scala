package loadusers

import java.io.File

import io.gatling.core.Predef._




class LoadSimulationSetUp extends Simulation{


  /**
    * Set the Cluster value that you would like to run load on
    * i.e SC1, HC15
    */
  val setCluster = "SC11"

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

  csvFile = new File("src/test/resources/data/".concat(setCluster).concat("LoadAgents.csv")).getAbsolutePath


    setCluster match {
   case "HC15" =>
     baseURL = "http://hc-c15web01/"
     phoneSkillId = "165861"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "f608ef92-a1e7-41e2-bd3d-570a0cee5778"
     workItemPoc =""
     workItemSkillId=""
   case "SC1" =>
     baseURL = "https://api-sc1.ucnlabext.com/"
     phoneSkillId = "165861"
     phoneScriptName="SpawnInboundPhone"
     chatPOC = "f608ef92-a1e7-41e2-bd3d-570a0cee5778"
     workItemPoc =""
     workItemSkillId=""
   case "SC11" =>
     baseURL = "https://api-sc11.ucnlabext.com/"
     phoneSkillId = "1622"
     phoneScriptName ="SpawnInboundPhone"
     workItemPoc = "218417"
     workItemSkillId = "1623"
   case "TO31" =>
   case "SO33" =>
 }

  val feeder = csv(csvFile).circular

}
