package Configurations
import Configurations.listOfPhones
import io.gatling.core.scenario.Simulation

class Helpers extends Simulation{

  def getPhone(): Long ={
    var phone = 999L
    var cont = 0
    var found = false
    do{
      var key = listOfPhones(cont)._1
      var value = listOfPhones(cont)._2
      if(key == true){
        phone = listOfPhones(cont)._2.toString().filter(_!='"').toLong
        listOfPhones(cont) = (false, value)
        found = true
      }
      cont += 1
    }while(cont < listOfPhones.length && !found)

    return phone
  }


}
