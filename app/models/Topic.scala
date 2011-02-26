package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class Topic(
  @Required
  var description: String
) extends Model {
}

object Topic extends QueryOn[Topic]
