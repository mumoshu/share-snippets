package controllers

import play.mvc._
import play.cache.Cache

import models._

object Users extends Controller with Secure {

  def posts = {
    Template("posts" -> Post.find("byUser", renderArgs.get("user", classOf[User])))
  }
}

object Posts extends Controller with Secure {

  def index = {
    val post = Post.random.getOrElse(null)
    val tags = if (post != null) post.taggings.map(_.tag) else "scala"
    Template
  }

  def create(tags: String, code: String, choice: String) = {
    val post = new Post(code, choice)

    if (post.validateAndSave) {
      tags.split(" ").foreach(user.tagPost(post, _))
      Cache.add("Post[" + post.id + "]", post)
      Action(show(post.id))
    } else {
      if (request.isAjax)
        ERROR
      else {
        flash.put("error_message", "validation fail")
        Action(index)
      }
    }
  }

  def update(postId: Long, tags: String, code: String, choice: String) = {
    val post = getPostOrNotFound(postId)

    post.code = code
    post.choice = choice
    post.save
    Action(show(postId))
  }

  def show(postId: Long) = {
    val post = getPostOrNotFound(postId)

    Template(post)
  }

  def edit(postId: Long) = {
    val post = getPostOrNotFound(postId)

    Template(post)
  }

  private def getPostOrNotFound(postId: Long) = {
    val post: Post = Cache.get("Post[" + postId + "]").getOrElse(Post.findById(postId).get)

    if (post == null)
      throw NotFound("post not found.")
    else
      post
  }

  def answer(postId: Long, answer: String) = {
    val post: Post = Cache.get("Post[" + postId + "]").getOrElse(Post.findById(postId).get)

    if (post == null) {
      NotFound("post not found.")
    } else {
      if (post.answerIsCorrect(answer)) {
        flash.put("message", "Good!")
      } else {
        flash.put("message", "Not good!: correct answer was: " + post.correctAnswers.mkString(", "))
      }
      Action(show(Post.random.map(_.id).getOrElse(0)))
    }
  }

  def tag(post: Post, tagName: String) = {
    val user = renderArgs.get("user", classOf[User])

    user.tagPost(post, tagName)
  }

  def recent = Template
}
