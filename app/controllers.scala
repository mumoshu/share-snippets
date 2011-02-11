package controllers

import play._
import play.mvc._

import models._

object Application extends Controller {
    
  def index = Posts.index
}

object Posts extends Controller {

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

  def recent = Template
}

package admin {
  object Langs extends Controller with CRUDFor[Lang]
  object Topics extends Controller with CRUDFor[Topic]
  //object Posts extends Controller with CRUDFor[Post]
}
