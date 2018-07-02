package CustomerProfiles
import io.gatling.core.Predef._
import com.chat_scenarios.inboundChat
import io.gatling.core.structure.ScenarioBuilder
import phone_scenarios.inboundPhone
import scala.concurrent.duration._

class QM_Profile extends Simulation{
  val phoneCall =  new inboundPhone
  val qm_chat = new inboundChat

  val qmenv : ScenarioBuilder = scenario("QM Analitics")
  setUp(qm_chat.ChatLoadTest.inject(rampUsers(1) over (3 seconds)))

}
