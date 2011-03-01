package models;

import play.data.validation._
import play.db.jpa._

@Entity
case class Post(
    @Required
    @Lob
    var code: String,
    @Required
    @Lob
    var choice: String)
  extends Model {

  def taggings = Tagging.find("byPost", this).fetch
  def correctAnswers = choice.split("\n").filter(c => c.startsWith("*"))
  def answerIsCorrect(answer: String) = correctAnswers.contains(answer.trim)
}

object Post extends QueryOn[Post] {
  def random: Option[Post] = Post.findById((math.random * count).floor.toLong)
}
