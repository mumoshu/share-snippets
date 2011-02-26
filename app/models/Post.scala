package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class Post(
  @ManyToOne
  @Required
  var lang: Lang,

  @ManyToOne
  var topic: Topic,

  @Required
  @Lob
  var code: String

) extends Model {

  def taggings = Tagging.find("byPost", this).fetch
}

object Post extends QueryOn[Post]
