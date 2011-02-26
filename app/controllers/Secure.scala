package controllers

import play.Logger

import play.mvc._

import models._

trait Secure extends Controller {
  def user: User = renderArgs.get("user").asInstanceOf[User]

  @Before def ensureLogin = {
    Logger.error("hoge")
    session("user.id") map(_.toLong) flatMap(User.findById(_)) match {
      case Some(user: User) => {
        Logger.debug("user.id: %s", user.id.toString)
        renderArgs += "user" -> user
        Continue
      }
      case None => {
        Logger.debug("redirecting to Application.index")
        flash.put("message", "Please login.")
        Action(Application.login)
      }
    }
  }
}
