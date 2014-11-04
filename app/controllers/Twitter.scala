package controllers

import play.api._
import play.api.libs.oauth._
import play.api.mvc._

object Twitter extends Controller {

  val API_KEY = Play.current.configuration.getString("twitter.api.key").get
  val API_SECRET = Play.current.configuration.getString("twitter.api.secret").get
  val KEY = ConsumerKey(API_KEY, API_SECRET)
  val CALLBACK_URL = Play.current.configuration.getString("twitter.callback.url").get

  val TWITTER = OAuth(ServiceInfo(
    "https://api.twitter.com/oauth/request_token",
    "https://api.twitter.com/oauth/access_token",
    "https://api.twitter.com/oauth/authorize", KEY),
    true)

  def authenticate = Action { request =>
    request.getQueryString("oauth_verifier").map { verifier =>
      val tokenPair = sessionTokenPair(request).get
      TWITTER.retrieveAccessToken(tokenPair, verifier) match {
        case Right(t) => {
          Redirect(routes.Analyze.analyze).withSession("token" -> t.token, "secret" -> t.secret)
        }
        case Left(e) => throw e
      }
    }.getOrElse(
      TWITTER.retrieveRequestToken(CALLBACK_URL) match {
        case Right(t) => {
          Redirect(TWITTER.redirectUrl(t.token)).withSession("token" -> t.token, "secret" -> t.secret)
        }
        case Left(e) => throw e
      })
  }

  def sessionTokenPair(implicit request: RequestHeader): Option[RequestToken] = {
    for {
      token <- request.session.get("token")
      secret <- request.session.get("secret")
    } yield {
      RequestToken(token, secret)
    }
  }
}