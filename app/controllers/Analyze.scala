package controllers

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.chasen.mecab.MeCab
import org.chasen.mecab.Tagger
import org.chasen.mecab.Node
import org.chasen.mecab.Model

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.oauth._

object Analyze extends Controller {

  def analyze = Action {
    val color = analyzeColorFromTweets
    Ok(color.toString)
  }

  def analyzeColorFromTweets: Int = {
    //TODO get tweets
    val colors = mutable.Map[Int, Int]()
    //foreach
    setColors(colors, analyzeColor(analyzeTweet("")))
    //TODO make Color
    val yourColor = 1
    yourColor
  }

  def getTweet = Action.async { implicit request =>
    Twitter.sessionTokenPair match {
      case Some(credentials) => {
        WS.url("https://api.twitter.com/1.1/statuses/home_timeline.json?count=1")//TODO
          .sign(OAuthCalculator(Twitter.KEY, credentials))
          .get
          .map(result => Ok(result.json))
      }
      case _ => Future.successful(Redirect(routes.Twitter.authenticate))
    }
  }

  def analyzeTweet(tweet: String): Node = {
    try {
      System.loadLibrary("MeCab")
    } catch {
      case e: UnsatisfiedLinkError => Logger.error("Not found libMeCab.so." + e)
      case _: Throwable => Logger.error("unknown error.")
    }
    val tagger: Tagger = new Tagger()
    val node: Node = tagger.parseToNode(tweet)
    Logger.debug("node " + node.getFeature() + ", " + node.getSurface())
    node
  }

  def analyzeColor(node: Node): (Int, Int) = {
    //TODO IMPL
    (1, 1)
  }

  def setColors(colors: mutable.Map[Int, Int], color: (Int, Int)) =
    colors.get(color._1) match {
      case some => colors.put(color._1, colors.getOrElse[Int](color._1, 0) + 1)
      case none => colors.put(color._1, color._2)
    }

}