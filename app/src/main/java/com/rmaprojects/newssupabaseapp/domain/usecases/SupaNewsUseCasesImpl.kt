package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import javax.inject.Inject

data class SupaNewsUseCasesImpl @Inject constructor(
    private val repository: SupaNewsRepository
): SupaNewsUseCases {
    override val loginUseCase
        get() = LoginUseCase(repository)
    override val registerUseCase: RegisterUseCase
        get() = RegisterUseCase(repository)
}