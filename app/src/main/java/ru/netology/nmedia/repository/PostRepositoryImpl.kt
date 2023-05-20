package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override val data: LiveData<List<Post>> =
        postDao.getAll().map { it.map(PostEntity::toDto) }


    override suspend fun getAll() {
        val response = PostApi.service.getAll()
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val posts = response.body() ?: throw RuntimeException("body is null")
        postDao.insert(posts.map { PostEntity.fromDto(it) })
    }

    override suspend fun likeById(id: Long) {
        postDao.likeById(id)
        val response = PostApi.service.likeById(id)
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
    }

    override suspend fun save(post: Post) {
        val response = PostApi.service.save(post)
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
        val posts = response.body() ?: throw RuntimeException("body is null")
        postDao.insert(PostEntity.fromDto(posts))
    }

    override suspend fun removeById(id: Long) {
        postDao.removeById(id)
        val response = PostApi.service.deletePostById(id)
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
    }

    override suspend fun unlikeById(id: Long) {
        postDao.likeById(id)
        val response = PostApi.service.unlikeById(id)
        if (!response.isSuccessful) {
            throw RuntimeException(response.message())
        }
    }
}





