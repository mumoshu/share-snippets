package models;

import play.data.validation._
import play.db.jpa._

import models._

@Entity
case class Lang(
  @Required
  var name: String
) extends Model {
}

object Lang extends QueryOn[Lang] {
  def findByNameOrCreate(name: String) = {
    find("byName", name).first.getOrElse(new Lang(name).save)
  }
}
