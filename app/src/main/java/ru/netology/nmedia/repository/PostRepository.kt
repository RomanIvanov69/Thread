package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import javax.security.auth.callback.Callback

interface PostRepository {
    fun getAllAsync(postsCallback:PostsCallback<List<Post>>)
    fun likeById(id: Long,postsCallback: PostsCallback<Post>)
    fun save(post: Post, postsCallback: PostsCallback<Post>)
    fun removeById(id: Long, postsCallback: PostsCallback<Unit>)
    fun unlikeById(id: Long,postsCallback: PostsCallback<Post>)

    interface PostsCallback<T> {
        fun onSuccess(value: T)
        fun onError(e: Exception)
    }
}
