package ru.netology.nmedia.dto

import java.io.FileDescriptor

data class Post(
    val id: Long = 0L,
    val author: String = "",
    val content: String = "", //Текст сообщения
    val published: Long = 0, //Дата и время публикации
    val likedByMe: Boolean = false, //Лайк включен или отключен
    val likes: Long = 0L, //Счётчик лайков
    val authorAvatar: String? = null,
    var attachment: Attachment? = null
)

data class Attachment(
    val url: String,
    val descriptor: String?,
    val type: AttachmentType,
)

enum class AttachmentType {
    IMAGE
}

