import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import controllers.Twitter

@RunWith(classOf[JUnitRunner])
class TwitterSpec extends Specification {

  "Twitter Authentication" should {

    "API_KEY と API_SECRET は application.conf から取得" in new WithApplication {
      Twitter.API_KEY must beEqualTo ("hoge")
      Twitter.API_SECRET must beEqualTo ("huga")
    }

  }

}

