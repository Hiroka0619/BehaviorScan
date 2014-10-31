package contorllers

import org.chasen.mecab.MeCab
import org.chasen.mecab.Tagger
import org.chasen.mecab.Node
import org.chasen.mecab.Model

import play.api._
import play.api.mvc._

object Analyze extends Controller {

  def analyze = Action {
    val color = analyzeColorFromTweets
    //TODO IMPL
    Ok("")
  }

  def analyzeColorFromTweets: Int = {
    //TODO get tweets
    //TODO analyze tweets
    //TODO analyze color
    val yourColor = 1
    yourColor
  }
  def analyzeTweet(tweet: String) {
    try {
      System.loadLibrary("mecab")
    } catch {
      case e: UnsatisfiedLinkError => Logger.error("" + e)
      case _: Throwable => Logger.error("")
    }
    val tagger: Tagger = new Tagger()
    val node: Node = tagger.parseToNode(tweet)
    val res = tagger.parse(tweet)
    Logger.debug("node " + node.getFeature())
    Logger.debug("res " + res)
  }
}