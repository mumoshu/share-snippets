package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class ConsumedPost(
  @Required
  var post: Post,
  @Required
  var consumer: User,
  @Required
  var answer: String
)

object ConsumedPost extends QueryOn[ConsumedPost]
