package com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.domain.model.News
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class FetchNewsFeed(
    private val repository: SupaNewsRepository
) {
    operator fun invoke(): Flow<ResponseState<List<News>>> {
        return repository.getAllNews()
    }
}