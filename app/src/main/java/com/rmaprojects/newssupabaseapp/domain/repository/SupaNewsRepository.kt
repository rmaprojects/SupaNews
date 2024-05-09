package com.rmaprojects.newssupabaseapp.domain.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsArticleDto
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import kotlinx.coroutines.flow.Flow

interface SupaNewsRepository {
    fun registerUser(
        username: String,
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>>
    fun loginUser(
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>>

    suspend fun getAllNews(): Result<Flow<List<NewsEntity>>>
    fun insertNews(news: NewsEntity): Flow<ResponseState<Boolean>>

    suspend fun unsubscribeNewsFeedChannel()
    fun getNewsArticle(newsId: Int): Flow<ResponseState<NewsArticleDto>>
    suspend fun unsubscribeNewsDetailChannel()
}