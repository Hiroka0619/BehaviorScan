import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import contorllers.Analyze

@RunWith(classOf[JUnitRunner])
class AnalyzeTweetSpec extends Specification {

  "AnalyzeTweet phase" should {
    
    "「今日は暖かい一日です」はいくつかの文節に区切られる" in {
      Analyze.analyzeTweet("「今日は暖かい一日です」")
      "" must beEqualTo ("")
    }
    
    "「暖かい」は形容詞である" in {
      "" must beEqualTo ("")
    }
    
  }

}
