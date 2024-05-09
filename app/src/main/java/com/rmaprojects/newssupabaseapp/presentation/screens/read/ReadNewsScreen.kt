package com.rmaprojects.newssupabaseapp.presentation.screens.read

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.rmaprojects.newssupabaseapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(navArgsDelegate = ReadNewsArguments::class)
fun ReadNewsScreen(
    viewModel: ReadNewsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }

    val newsDetailState = viewModel.newsDetailState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "SupaNews",
                    fontWeight = FontWeight.Bold
                )
            })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            newsDetailState.value.DisplayResult(
                onLoading = { CircularProgressIndicator() },
                onError = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Failed")
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "")
                        }
                    }
                },
                onSuccess = {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            ) {
                                Text(
                                    text = "Mamang is on Fire!",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.displayMedium
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Person Image"
                                        )
                                        Text(
                                            text = "Mamang Sumamang",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                    Text(
                                        text = "Dates",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            AsyncImage(
                                model = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(156.dp),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                fallback = painterResource(id = R.drawable.no_img)
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Content",
                                textAlign = TextAlign.Justify,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            )
        }
    }
}