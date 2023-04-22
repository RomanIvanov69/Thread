package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit


class PostRepositoryImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {

        private val jsonType = "application/json".toMediaType()
    }

    override fun getAllAsync(postsCallback: PostRepository.PostsCallback<List<Post>>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    try {
                        postsCallback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        postsCallback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    postsCallback.onError(e)
                }
            })
    }

    override fun save(post: Post, postsCallback: PostRepository.PostsCallback<Post>) {
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    postsCallback.onSuccess(gson.fromJson(body, typeToken.type))
                }

                override fun onFailure(call: Call, e: IOException) {
                    postsCallback.onError(e)
                }
            })
    }

    override fun removeById(id: Long, postsCallback: PostRepository.PostsCallback<Unit>) {
        val request: Request = Request.Builder()
            .delete()
            .url("${BASE_URL}/api/slow/posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    postsCallback.onSuccess(gson.fromJson(body, typeToken.type))
                }

                override fun onFailure(call: Call, e: IOException) {
                    postsCallback.onError(e)
                }
            })
    }

    override fun likeById(id: Long, postsCallback: PostRepository.PostsCallback<Post>) {
        val request: Request = Request.Builder()
            .post(gson.toJson(id).toRequestBody(jsonType))
            .url("${BASE_URL}/api/posts/$id/likes")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    postsCallback.onSuccess(gson.fromJson(body, typeToken.type))
                }

                override fun onFailure(call: Call, e: IOException) {
                    postsCallback.onError(e)
                }
            })
    }


    override fun unlikeById(id: Long, postsCallback: PostRepository.PostsCallback<Post>) {
        val request: Request = Request.Builder()
            .delete(gson.toJson(id).toRequestBody(jsonType))
            .url("${BASE_URL}/api/posts/$id/likes")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string() ?: throw RuntimeException("body is null")
                    postsCallback.onSuccess(gson.fromJson(body, typeToken.type))
                }

                override fun onFailure(call: Call, e: IOException) {
                    postsCallback.onError(e)
                }
            })
    }

}



