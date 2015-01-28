package models.utils

import play.api.Play.current
import scalikejdbc._
import com.typesafe.config._

trait DBConn {
  val appConf = ConfigFactory.load()
  val dbUrl = appConf.getString("db.default.url")
  val driverName = appConf.getString("db.default.driver")
  val dbUser = appConf.getString("db.default.user")
  val dbPassword = appConf.getString("db.default.password")
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(dbUrl, dbUser, dbPassword)
}

object Databases {
  
}
