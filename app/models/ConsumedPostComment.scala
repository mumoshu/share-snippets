package models

import play.data.validation._
import play.db.jpa._

@Entity
case class ConsumedPostComment(
  @Required
  var consumedPost: Post,
  @Required
  var body: String
)

object ConsumedPostComment extends QueryOn[ConsumedPostComment]
