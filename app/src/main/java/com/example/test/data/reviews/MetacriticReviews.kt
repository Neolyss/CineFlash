package com.example.test.data.reviews

data class MetacriticReviews(
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val items: List<MetacriticReview>,
    val title: String,
    val type: String,
    val year: String
)