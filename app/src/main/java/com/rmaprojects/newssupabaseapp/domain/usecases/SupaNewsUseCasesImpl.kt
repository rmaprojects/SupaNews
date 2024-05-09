package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.LoginUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.RegisterUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.AddNewNews
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.NewsFeedUseCases
import javax.inject.Inject

data class SupaNewsUseCasesImpl @Inject constructor(
    private val repository: SupaNewsRepository
): SupaNewsUseCases {
    override val loginUseCase
        get() = LoginUseCase(repository)
    override val registerUseCase: RegisterUseCase
        get() = RegisterUseCase(repository)
    override val addNewNews: AddNewNews
        get() = AddNewNews(repository)
    override val newsFeedUseCases: NewsFeedUseCases
        get() = NewsFeedUseCases(repository)
}