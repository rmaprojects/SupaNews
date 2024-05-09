package com.rmaprojects.newssupabaseapp.domain.usecases.newsread

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsArticleDto
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class FetchNewsUseCase(
    private val repository: SupaNewsRepository
) {

    operator fun invoke(newsId: Int): Flow<ResponseState<NewsArticleDto>> {
        return repository.getNewsArticle(newsId)
    }

}