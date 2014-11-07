package controllers

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.chasen.mecab.Tagger

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.oauth._
import play.api.libs.ws._

object Analyze extends Controller {

  val API_URL = Play.current
                    .configuration
                    .getString("twitter.api.url")
                    .getOrElse("https://api.twitter.com/1.1/statuses/user_timeline.json")
  val API_COUNT = Play.current.configuration.getString("twitter.api.count").getOrElse(1000)

  def analyze = Action.async { implicit request =>
    Twitter.sessionTokenPair match {
      case Some(credentials) => {
          WS.url(API_URL + "?count=" + API_COUNT)
            .sign(OAuthCalculator(Twitter.KEY, credentials))
            .get
            .map(res => analyzeColorFromTweets(res.json))
        }
      case _ => Future.successful(Redirect(routes.Application.authError))
    }
  }

  def analyzeColorFromTweets(json: JsValue): Result = {
    val colors = mutable.Map[Int, Int]()
    splitJson(json) foreach { tweet =>
      setColors(colors, analyzeColor(tweet))
    }
    val yourColor = makeColor(colors)
    Ok(yourColor.toString)
  }

  def splitJson(json: JsValue): List[String] = {
    val textList = mutable.ListBuffer[String]()
    json \\ ("text") foreach { js => textList += js.toString }
    textList.result
  }

  def analyzeColor: String => Map[Int, Int] = { tweet =>
    val tagger = new Tagger()
    pickup(tagger.parse(tweet).lines) foreach { w =>
      Logger.debug(w)//TODO 問い合わせクエリ作成
    }
    //TODO DB等問い合わせ、出現色返却
    Map.apply(1 -> 1)
  }

  val pickup: Iterator[String] => List[String] = { words =>
    val targetWords =  mutable.ListBuffer[String]()
    words foreach { word =>
      word match {
        case w if w.contains("形容詞")
                | w.contains("動詞")
          => targetWords += word.take(word.indexOf("\t"))//getSurface()
        case _ => //Nothing to do
      }
    }
    targetWords.result
  }

  def setColors(summary: mutable.Map[Int, Int], colors: Map[Int, Int]) = {
    colors.keySet foreach { color =>
      summary.contains(color) match {
        case true => summary.put(color, summary.getOrElse[Int](color, 0) + colors.getOrElse(color, 0))
        case false => summary.put(color, colors.getOrElse(color, 0))
      }
    }
  }

  def makeColor(colors: mutable.Map[Int, Int]): Int = {
    1
  }
}