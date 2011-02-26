package controllers

import play.mvc._

import models._

object Posts extends Controller with Secure {

  def index = {
    val lan = new Lang("lang")
    lan.save
    val topic = new Topic("topic")
    topic.save
    val posts = Post.findAll
    Template(lan, topic, posts)
  }

  def create(language: String, tags: String, code: String) = {
    val lang = Lang.findByNameOrCreate(language)
    val post = new Post(lang, null, code)
    post.validateAndSave
    tags.split(" ").foreach(user.tagPost(post, _))
    Cache.add("Post[" + post.id + "]", post)
    Action(show(post.id))
  }

  def show(postId: Long) = {
    val post: Post = Cache.get("Post[" + postId + "]").getOrElse(Post.findById(postId).get)

    if (post == null)
      NotFound("post not found.")

    Template(post)
  }

  def answer(postId: Long, answer: String) = {
    val post: Post = Cache.get("Post[" + postId + "]").getOrElse(Post.findById(postId).get)

    if (post == null)
      NotFound("post not found.")

    val consumedPost = new ConsumedPost

    consumedPost.post = post
    consumedPost.answer = answer

    consumedPost.save
  }

  def answers = {
    val answers = ConsumedPost.findAll

    Template(answers)
  }

  def tag(post: Post, tagName: String) = {
    val user = renderArgs.get("user", classOf[User])

    user.tagPost(post, tagName)
  }

  def recent = Template
}
