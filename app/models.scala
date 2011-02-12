package models

import play.db.jpa._
import play.data.validation._

import java.util.{List => JList}

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

  def taggings = Tagging.find("byPost", this)
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
