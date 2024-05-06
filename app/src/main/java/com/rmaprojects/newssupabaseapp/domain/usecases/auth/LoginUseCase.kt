package com.rmaprojects.newssupabaseapp.domain.usecases.auth

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: SupaNewsRepository
) {
    operator fun invoke(
        email: String, password: String
    ): Flow<ResponseState<LocalUser>> {
        return repository.loginUser(
            email, password
        )
    }
}