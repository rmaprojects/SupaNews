package com.rmaprojects.newssupabaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.presentation.screens.NavGraphs
import com.rmaprojects.newssupabaseapp.presentation.screens.destinations.AuthScreenDestination
import com.rmaprojects.newssupabaseapp.presentation.screens.destinations.NewsFeedScrenDestination
import com.rmaprojects.newssupabaseapp.ui.theme.SupaNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            SupaNewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = if (LocalUser.username.isNullOrEmpty()) AuthScreenDestination else NewsFeedScrenDestination
                    )
                }
            }
        }
    }
}