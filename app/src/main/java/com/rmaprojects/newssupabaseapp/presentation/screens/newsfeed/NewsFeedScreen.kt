package com.rmaprojects.newssupabaseapp.presentation.screens.newsfeed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmaprojects.newssupabaseapp.presentation.components.newsfeed.NewsItem
import com.rmaprojects.newssupabaseapp.presentation.screens.destinations.AddNewsScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun NewsFeedScreen(
    navigator: DestinationsNavigator,
    viewModel: NewsFeedViewModel = hiltViewModel()
) {

    val newsFeedState =
        viewModel.newsFeedState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.connectToRealtime()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "SupaNews",
                        fontWeight = FontWeight.Bold,
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.navigate(AddNewsScreenDestination) {
                        restoreState = true
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new News"
                )
            }
        }
    ) { innerPadding ->
        newsFeedState.value.DisplayResult(
            onLoading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            },
            onError = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(it)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.connectToRealtime() }) {
                        Text(text = "Retry?")
                    }
                }
            },
            onSuccess = { newsList ->
                if (newsList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Tidak ada berita tersedia di platform ini 🙏🏻")
                    }
                } else
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        items(newsList) { news ->
                            NewsItem(
                                modifier = Modifier.padding(12.dp),
                                title = news.title,
                                content = news.content,
                                author = if (news.authorId == null) "${news.authorUsername} [DELETED]" else news.authorUsername,
                                headerImg = news.headerImgUrl,
                                onItemClicked = { /*TODO*/ }
                            )
                        }
                    }
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.leaveRealtimeChannel() }
    }

}