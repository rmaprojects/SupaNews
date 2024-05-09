package com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class AddNewNews(
    private val repository: SupaNewsRepository
) {

    operator fun invoke(news: NewsEntity): Flow<ResponseState<Boolean>> {
        return repository.insertNews(news)
    }

}