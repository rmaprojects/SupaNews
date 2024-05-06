package com.rmaprojects.newssupabaseapp.domain.model

data class News(
    val title: String,
    val content: String,
    val headerImgUrl: String,
    val authorUsername: String,
    val authorId: String
)