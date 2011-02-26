package controllers

import play.mvc._

import models._

object Application extends Controller {

  def index = Posts.index

  def login = Template

  def authenticate(userId: Long) = {
    session.put("user.id", userId)
    renderArgs += "user" -> User.findById(userId).getOrNotFound
    Action(Posts.index)
  }

  def signup(userName: String) = {
    val user = new User(userName).save
    session.put("user.id", user.id)
    renderArgs += "user" -> user
    Action(Posts.index)
  }
}
