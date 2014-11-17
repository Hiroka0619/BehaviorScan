package models

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

object BSTables {
  case class ColorChart (
    color_id: Int,
    color: (Int, Int, Int),
    colorName: String,
    emotion: Int,
    emotionName: Int
  )
  case class EmotionalWord(
    word: String,
    emotion: Int)
  
  object ColorChart extends DBConn {
    val * = (rs: WrappedResultSet) => ColorChart(
      rs.int("color_id"),
      RGBCode(rs.string("color")),
      rs.string("colorName"),
      rs.int("emotion"),
      rs.int("emotionName"))
      
    val RGBCode: String => (Int, Int, Int) = { str =>
      val arr = str.split(",")
      (arr(0).toInt, arr(1).toInt, arr(2).toInt)
    }
    
    val RGBString: Tuple3[Int, Int, Int] => String = { code =>
        code._1 + "," + code._2 + "," + code._3
    }
    
    val readById = (id: String) => DB readOnly { implicit session => 
      SQL("SELECT * FROM color_chart WHERE color_id = ?").bind(id).map(*).list.apply()
    }
    
    val readAll = () => DB readOnly { implicit session => 
      SQL("SELECT * FROM color_chart").map(*).list.apply()
    }
    /*
    val insert = (kamoku_mei: String) => DB autoCommit { implicit session =>
      SQL("INSERT INTO color_chart (kamoku_mei) VALUES (?)").bind(kamoku_mei).update.apply()
    }
    */
  }
  
}
