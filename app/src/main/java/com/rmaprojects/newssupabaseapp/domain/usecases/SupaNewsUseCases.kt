package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.newssupabaseapp.domain.usecases.auth.LoginUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.auth.RegisterUseCase
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.FetchNewsFeed
import com.rmaprojects.newssupabaseapp.domain.usecases.newsfeed.InsertNewNews

interface SupaNewsUseCases {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
    val fetchNewsFeed: FetchNewsFeed
    val insertNewNews: InsertNewNews
}