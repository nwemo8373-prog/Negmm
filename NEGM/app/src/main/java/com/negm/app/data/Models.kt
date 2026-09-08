package com.negm.app.data

data class NegmUser(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val bio: String = "",
    val avatarUrl: String = "",
    val verified: Boolean = false,
    val online: Boolean = false
)

data class NegmMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = 0L,
    val type: String = "text"
)

data class Conversation(
    val id: String = "",
    val userId: String = "",
    val lastMessage: String = "",
    val timestamp: Long = 0L
)

data class Story(
    val id: String = "",
    val userId: String = "",
    val mediaUrl: String = "",
    val mediaType: String = "image",
    val timestamp: Long = 0L
)
