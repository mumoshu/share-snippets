package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class Tag(
  @Required
  var name: String
) extends Model

object Tag extends QueryOn[Tag] {
  def findByNameOrCreate(name: String) = {
    find("byName", name).first.getOrElse(new Tag(name).save)
  }
}
