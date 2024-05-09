package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.newssupabaseapp.domain.usecases.auth.LoginUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.RegisterUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.NewsFeedUseCases
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.AddNewNews
import com.rmaprojects.newssupabaseapp.domain.usecases.newsread.FetchNewsUseCase

interface SupaNewsUseCases {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
    val addNewNews: AddNewNews
    val newsFeedUseCases: NewsFeedUseCases
    val fetchNewsUseCase: FetchNewsUseCase
}