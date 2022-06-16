package com.example.test.data.reviews

data class UserReviews(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val items: List<UserReview>,
    val title: String,
    val type: String,
    val year: String
)