import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import models.BSTables._

@RunWith(classOf[JUnitRunner])
class BSTablesSpec extends Specification {

  "AnalyzeTweet phase" should {

    "文字列 255,255,255 は 数値型トリプル (255,255,255) に変換されること" in {
      val res = ColorChart.RGBCode("255,255,255")
      res must beEqualTo ((255,255,255))
    }

    "数値型トリプル (255,255,255) は 文字列 255,255,255 に変換されること" in {
      val res = ColorChart.RGBString((255,255,255))
      res must beEqualTo ("255,255,255")
    }

  }

}