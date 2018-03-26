package test

import com.MediaTypes.APITest
import io.gatling.core.Predef.{nothingFor, _}

import scala.concurrent.duration._

class BurstLogins extends Simulation{

 val loginTest = new APITest
  setUp(loginTest.changeState.inject(atOnceUsers(100),nothingFor(1 minute),
   atOnceUsers(300),nothingFor(1 minute),
   atOnceUsers(500),nothingFor(1 minute),
   atOnceUsers(1000),nothingFor(1 minute),
   atOnceUsers(1500),nothingFor(1 minute),
   atOnceUsers(2000),nothingFor(1 minute),
   atOnceUsers(2500),nothingFor(1 minute),
   atOnceUsers(3000),nothingFor(1 minute),
   atOnceUsers(3500),nothingFor(1 minute),
   atOnceUsers(4000),nothingFor(1 minute),
   atOnceUsers(4500),nothingFor(1 minute),
   atOnceUsers(5000)))

}
