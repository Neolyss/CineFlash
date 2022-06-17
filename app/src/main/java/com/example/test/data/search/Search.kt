package com.example.test.data.search

data class Search(
    val errorMessage: String,
    val expression: String,
    val results: List<Result>,
    val searchType: String
)