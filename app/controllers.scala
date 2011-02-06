package controllers

import play._
import play.mvc._

import models._

object Application extends Controller {
    
  def index = Posts.index
}

object Posts extends Controller {

  def index {
    val post = new Post(new Lang("lang"), new Topic("topic"), "code")
    Template
  }

  def create(lang: Lang, topic: Topic, code: String) {
    val post = new Post(lang, topic, code)
    post.validateAndSave
    show(post)
  }

  def show(post: Post) = Template

  def recent = Template
}
