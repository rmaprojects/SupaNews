package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.LoginUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.RegisterUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.FetchNewsFeed
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.InsertNewNews
import javax.inject.Inject

data class SupaNewsUseCasesImpl @Inject constructor(
    private val repository: SupaNewsRepository
): SupaNewsUseCases {
    override val loginUseCase
        get() = LoginUseCase(repository)
    override val registerUseCase: RegisterUseCase
        get() = RegisterUseCase(repository)
    override val fetchNewsFeed: FetchNewsFeed
        get() = FetchNewsFeed(repository)
    override val insertNewNews: InsertNewNews
        get() = InsertNewNews(repository)
}