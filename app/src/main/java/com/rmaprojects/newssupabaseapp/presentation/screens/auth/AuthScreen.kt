package com.rmaprojects.newssupabaseapp.presentation.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.presentation.screens.destinations.AuthScreenDestination
import com.rmaprojects.newssupabaseapp.presentation.screens.destinations.NewsFeedScrenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@RootNavGraph(true)
@Destination
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    var currentContent by rememberSaveable {
        mutableIntStateOf(0)
    }

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var username by rememberSaveable {
        mutableStateOf("")
    }

    val loginState = viewModel.loginState.collectAsStateWithLifecycle(ResponseState.Idle)
    val registerState = viewModel.registerState.collectAsStateWithLifecycle(ResponseState.Idle)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = if (currentContent == 1) "Register User" else "Login User")
                }
            )
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = currentContent == 0,
            modifier = Modifier.padding(innerPadding)
        ) {
            LoginContent(
                email = email,
                password = password,
                onEmailChanged = {
                    email = it
                },
                onPasswordChanged = {
                    password = it
                },
                onRegisterClick = {
                    currentContent = 1
                },
                onLoginClick = {
                    viewModel.performLogin(email, password)
                },
                loginState = loginState,
                onSuccessLogin = {
                    navigator.navigate(NewsFeedScrenDestination) {
                        popUpTo(AuthScreenDestination) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = currentContent == 1,
            modifier = Modifier.padding(innerPadding)
        ) {
            RegisterContent(
                email = email,
                password = password,
                username = username,
                onUsernameChanged = {
                    username = it
                },
                onEmailChanged = {
                    email = it
                },
                onPasswordChanged = {
                    password = it
                },
                onLoginClick = {
                    currentContent = 0
                },
                onRegisterClick = {
                    viewModel.performRegister(username, email, password)
                },
                registerState = registerState,
                onSuccessRegistration = {
                    navigator.navigate(NewsFeedScrenDestination) {
                        popUpTo(AuthScreenDestination) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }

}

