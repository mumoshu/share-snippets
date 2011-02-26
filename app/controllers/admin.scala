package controllers

import play.mvc._

import models._

package object admin {
  object Langs extends Controller with CRUDFor[Lang]
  object Topics extends Controller with CRUDFor[Topic]
  object Postss extends Controller with CRUDFor[Post]
  object Users extends Controller with CRUDFor[User]
  object Taggings extends Controller with CRUDFor[Tagging]
}
