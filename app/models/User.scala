package models

import play.data.validation._
import play.db.jpa._

@Entity
case class User(
  @Required
  var name: String
) extends Model {

  def tagPost(post: Post, tagName: String) = {
    val tag = Tag.findByNameOrCreate(tagName)

    Tagging.createIfNotFound(this, post, tag)
  }
}

object User extends QueryOn[User]
