package com.example.test.data.reviews

data class UserReview(
    val content: String,
    val date: String,
    val helpful: String,
    val rate: String,
    val reviewLink: String,
    val title: String,
    val userUrl: String,
    val username: String,
    val warningSpoilers: Boolean
)