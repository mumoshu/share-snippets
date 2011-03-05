package controllers

import play.mvc._

import models._

package admin {
  object Postss extends Controller with CRUDFor[Post]
  object Userss extends Controller with CRUDFor[User]
  object Taggingss extends Controller with CRUDFor[Tagging]
}
