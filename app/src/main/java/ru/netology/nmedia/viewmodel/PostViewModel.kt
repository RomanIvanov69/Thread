package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import kotlin.concurrent.thread

private val empty = Post(
    id = 0,
    author = "Me",
    content = "",
    published = 0L,
    likedByMe = false,
    likes = 0,
    authorAvatar = null
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl()

    private val _data = MutableLiveData<FeedModel>()
    val data: LiveData<FeedModel>
        get() = _data

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    private val edited = MutableLiveData(empty)

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.value = FeedModel(loading = true)
        repository.getAllAsync(object : PostRepository.PostsCallback<List<Post>> {
            override fun onSuccess(info: List<Post>) {
                _data.value = FeedModel(posts = info, empty = info.isEmpty())
            }

            override fun onError(e: Exception) {
                _data.value = FeedModel(error = true, serverError = true)
            }
        })
    }

    fun save() {
        edited.value?.let {
            repository.save(it, object : PostRepository.PostsCallback<Post> {
                override fun onSuccess(post: Post) {
                    _postCreated.postValue(Unit)
                }

                override fun onError(e: Exception) {
                    edited.value = empty
                }
            })
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(post: Post) {
        _data.postValue(FeedModel(loading = true))
        val old = data.value?.posts.orEmpty()
        if (post.likedByMe) repository.unlikeById(post.id, object : PostRepository.PostsCallback<Post> {
            override fun onSuccess(value: Post) {
                val new = old.map { if (it.id == post.id) value else it }
                _data.postValue(_data.value?.copy(posts = new))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
        else repository.likeById(post.id, object : PostRepository.PostsCallback<Post> {
            override fun onSuccess(value: Post) {
                val new = old.map { if (it.id == post.id) value else it }
                _data.postValue(_data.value?.copy(posts = new))
            }

            override fun onError(e: Exception) {
                _data.postValue(FeedModel(error = true))
            }
        })
    }

    fun removeById(id: Long) {
        val old = _data.value?.posts.orEmpty()
        repository.removeById(id, object : PostRepository.PostsCallback<Unit> {
            override fun onSuccess(value: Unit) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        })
    }
}


