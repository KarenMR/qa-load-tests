package LoadProfiles

import InContactAPI.AdminAPI
import io.gatling.core.Predef._
import phone_scenarios.inboundPhone
import workItem_scenarios.workItem
import InContactAPI.inContactAuth
import com.MediaTypes.APITest
import io.gatling.core.structure.ScenarioBuilder
import loadusers.LoadSimulationSetUp

import scala.concurrent.duration._
import scala.language.postfixOps

class MediumLoadProfile extends Simulation {}