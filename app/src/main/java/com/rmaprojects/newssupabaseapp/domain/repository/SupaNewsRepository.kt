package com.rmaprojects.newssupabaseapp.domain.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.model.UsersEntity
import com.rmaprojects.newssupabaseapp.domain.model.News
import io.github.jan.supabase.realtime.RealtimeChannel
import kotlinx.coroutines.flow.Flow

interface SupaNewsRepository {
    fun insertNews(news: News): Flow<ResponseState<Boolean>>
    fun registerUser(
        username: String,
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>>
    fun loginUser(
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>>
    val newsRealtimeChannel: RealtimeChannel
    fun getAllNews(): Flow<ResponseState<List<News>>>
}