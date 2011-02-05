package models

import play.db.jpa._
import play.data.validation._

@Entity
case class Post(
  @ManyToOne
  @Required
  var lang: Lang,

  @ManyToOne
  @Required
  var topic: Topic,

  @Required
  @Lob
  var code: String
) extends Model {
}

object Post extends QueryOn[Post]

@Entity
case class Lang(
  @Required
  var name: String
) extends Model {
}

object Lang extends QueryOn[Lang]

@Entity
case class Topic(
  @Required
  var description: String
) extends Model {
}

object Topic extends QueryOn[Topic]
