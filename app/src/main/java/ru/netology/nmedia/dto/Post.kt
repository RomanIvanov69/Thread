package ru.netology.nmedia.dto

data class Post(
    val id: Long = 0L,
    val author: String = "",
    val content: String = "", //Текст сообщения
    val published: Long = 0, //Дата и время публикации
    val likedByMe: Boolean = false, //Лайк включен или отключен
    val likes: Long = 0L, //Счётчик лайков
    val authorAvatar: String? = null
)

