package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class Tagging(
  @Required
  var user: User,

  @Required
  var post: Post,

  @Required
  var tag: Tag
) extends Model

object Tagging extends QueryOn[Tagging] {
  def createIfNotFound(user: User, post: Post, tag: Tag) = {
    find("byUserAndPostAndTag", user, post, tag).first.getOrElse(
      new Tagging(user, post, tag).save
    )
  }
}
