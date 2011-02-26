package models

import play.data.validation._
import play.db.jpa._

// Model, QueryOnのメソッド内でfind/createするのは一回だけにする。
// ２回も３回もDB操作している場合は、そのメソッドに役割が集中しすぎているのではないかという仮説から。

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
