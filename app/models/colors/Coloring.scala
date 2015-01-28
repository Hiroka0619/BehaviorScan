package models.colors

import models.utils._
import scalikejdbc._

case class Coloring (
  color_id: Int,
  color: Color,
  colorName: String,
  emotion: Int,
  emotionName: Int)

object Coloring extends DBConn {
  val * = (rs: WrappedResultSet) => Coloring(
    rs.int("color_id"),
    Color(rs.int("color_id"), 255, 255, 255),
    rs.string("colorName"),
    rs.int("emotion"),
    rs.int("emotionName"))

  val readById = (id: String) => DB readOnly { implicit session => 
    SQL("SELECT * FROM coloring WHERE color_id = ?").bind(id).map(*).list.apply()
  }
  
  val readAll = () => DB readOnly { implicit session => 
    SQL("SELECT * FROM coloring").map(*).list.apply()
  }
  /*
  val insert = (kamoku_mei: String) => DB autoCommit { implicit session =>
    SQL("INSERT INTO color_chart (kamoku_mei) VALUES (?)").bind(kamoku_mei).update.apply()
  }
  */
}
