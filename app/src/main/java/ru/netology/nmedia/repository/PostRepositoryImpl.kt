package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Callback
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.Post


class PostRepositoryImpl : PostRepository {

    override fun getAllAsync(postsCallback: PostRepository.PostsCallback<List<Post>>) {
        PostApi.service.getAll()
            .enqueue(object : Callback<List<Post>> {
                override fun onResponse(
                    call: Call<List<Post>>,
                    response: retrofit2.Response<List<Post>>
                ) {
                    if (!response.isSuccessful) {
                        postsCallback.onError(RuntimeException(response.message()))
                        return
                    }
                    postsCallback.onSuccess(
                        response.body() ?: throw RuntimeException("body is null")
                    )
                }

                override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                    postsCallback.onError(RuntimeException(t))
                }
            })
    }

    override fun save(post: Post, postsCallback: PostRepository.PostsCallback<Post>) {
        PostApi.service.save(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    if (!response.isSuccessful) {
                        postsCallback.onError(RuntimeException(response.message()))
                        return
                    }
                    postsCallback.onSuccess(
                        response.body() ?: throw RuntimeException("body is null")
                    )

                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    postsCallback.onError(RuntimeException(t))
                }
            })
    }

    override fun removeById(id: Long, postsCallback: PostRepository.PostsCallback<Unit>) {
        PostApi.service.deletePostById(id)
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: retrofit2.Response<Unit>) {
                    if (!response.isSuccessful) {
                        postsCallback.onError(RuntimeException(response.message()))
                        return
                    }
                    postsCallback.onSuccess(
                        response.body() ?: throw RuntimeException("body is null")
                    )
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    postsCallback.onError(RuntimeException(t))
                }

            })
    }

    override fun likeById(id: Long, postsCallback: PostRepository.PostsCallback<Post>) {
        PostApi.service.likeById(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    if (!response.isSuccessful) {
                        postsCallback.onError(RuntimeException(response.message()))
                        return
                    }
                    postsCallback.onSuccess(
                        response.body() ?: throw RuntimeException("Body is null")
                    )
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    postsCallback.onError(RuntimeException(t))
                }

            })
    }


    override fun unlikeById(id: Long, postsCallback: PostRepository.PostsCallback<Post>) {
        PostApi.service.likeById(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                    if (!response.isSuccessful) {
                        postsCallback.onError(RuntimeException(response.message()))
                        return
                    }
                    postsCallback.onSuccess(
                        response.body() ?: throw RuntimeException("Body is null")
                    )
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    postsCallback.onError(RuntimeException(t))
                }

            })

    }

}



