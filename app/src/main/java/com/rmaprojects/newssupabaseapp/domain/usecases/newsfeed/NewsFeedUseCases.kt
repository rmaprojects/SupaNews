package com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed

import android.util.Log
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class NewsFeedUseCases(
    private val repository: SupaNewsRepository
) {
    suspend fun fetchAllNews(): Result<Flow<List<NewsEntity>>> {
        return repository.getAllNews()
    }

    suspend fun unsubscribeChannel() {
        repository.unsubscribeChannel()
    }
}