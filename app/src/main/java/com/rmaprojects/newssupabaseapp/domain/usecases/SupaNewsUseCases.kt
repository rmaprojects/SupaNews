package com.rmaprojects.newssupabaseapp.domain.usecases

interface SupaNewsUseCases {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
}