package controllers

import play.mvc._

import models._

object Usages extends Controller {
  /**
   * Invoked when requested /usages/bind_post?post.id=<id>.<br />
   * <br />
   * post is binded with <code>Post.findById(id).getOrElse({ val post = new Post; post.id = id; post })</code>
   */
  def bind_post(post: Post) = if (post != null) "id=%d, code=%s".format(post.id, post.code)
}
