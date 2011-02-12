package controllers

import play._
import play.mvc._

import models._

object Application extends Controller {
    
  def index = Posts.index

  def login = Template

  def authenticate(userId: Long) = {
    session.put("user.id", userId)
    renderArgs += "user" -> User.findById(userId).getOrNotFound
    Action(index)
  }
}

object Usages extends Controller {
  /**
   * Invoked when requested /usages/bind_post?post.id=<id>.<br />
   * <br />
   * post is binded with <code>Post.findById(id).getOrElse({ val post = new Post; post.id = id; post })</code>
   */
  def bind_post(post: Post) = if (post != null) "id=%d, code=%s".format(post.id, post.code)
}

object Posts extends Controller with Secured {

  def index = {
    val lan = new Lang("lang")
    lan.save
    val topic = new Topic("topic")
    topic.save
    Template(lan, topic)
  }

  def create(lang: Lang, topic: Topic, code: String) = {
    val post = new Post(lang, topic, code)
    post.validateAndSave
    Action(show(post))
  }

  def show(post: Post) = Template(post)

  def tag(post: Post, tagName: String) = {
    val user = renderArgs.get("user", classOf[User])

    user.tagPost(post, tagName)
  }

  def recent = Template
}

trait Secure extends Controller {
  @Before def ensureLogin = {
    session("user.id") map(_.toLong) match {
      case Some(userId: Long) => renderArgs += "user" -> User.findById(userId).getOrNotFound; Continue
      case None => Action(Application.login)
    }
  }
}

package admin {
  object Langs extends Controller with CRUDFor[Lang]
  object Topics extends Controller with CRUDFor[Topic]
  //object Posts extends Controller with CRUDFor[Post]
}
