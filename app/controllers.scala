package controllers

import play._
import play.mvc._

import models._

object Application extends Controller {
    
  def index = Posts.index
}

object Posts extends Controller {

  def index = Template

  def create(lang: Lang, topic: Topic, code: String) {
    new Post(lang, topic, code).validateAndSave
    recent
  }

  def recent = Template
}
