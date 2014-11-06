import org.specs2.mutable._
import org.specs2.runner._

import org.junit.runner._

import org.chasen.mecab.Tagger
import org.chasen.mecab.Node

import play.api.test._
import play.api.test.Helpers._

import controllers.Analyze

@RunWith(classOf[JUnitRunner])
class AnalyzeColorSpec extends Specification {

  "AnalyzeColor" should {

    "「好き」は「色」として判定されること" in new WithApplication {
      val tagger = new Tagger()
      val testNode: String => Node = tagger.parseToNode(_)
      val color = Analyze.analyzeColor(testNode("好き"))
      color.get(1).get must beEqualTo (1)
    }

  }
}
