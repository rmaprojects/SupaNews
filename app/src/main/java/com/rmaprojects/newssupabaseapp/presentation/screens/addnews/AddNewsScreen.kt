package com.rmaprojects.newssupabaseapp.presentation.screens.addnews

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.R
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.presentation.components.common.ErrorBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun AddNewsScreen(
    viewModel: AddNewsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val context = LocalContext.current

    val insertState = viewModel.insertState.collectAsStateWithLifecycle(
        ResponseState.Idle
    )

    var newsTitle by rememberSaveable {
        mutableStateOf("")
    }

    var newsContent by rememberSaveable {
        mutableStateOf("")
    }

    var newsUrl by rememberSaveable {
        mutableStateOf("")
    }

    var displayNewsUrl by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Insert News")
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = newsUrl,
                    onValueChange = {
                        newsUrl = it
                    },
                    placeholder = {
                        Text(text = "Header Image URL")
                    },
                    suffix = {
                        TextButton(
                            modifier = Modifier.height(36.dp),
                            onClick = {
                                displayNewsUrl = newsUrl
                                Log.d("DISPLAY_NEWS", displayNewsUrl.toString())
                            }) {
                            Text(text = "Check")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (displayNewsUrl != null) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp),
                        model = displayNewsUrl,
                        contentDescription = newsTitle,
                        contentScale = ContentScale.Crop,
                        fallback = painterResource(id = R.drawable.no_img)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = newsTitle,
                    onValueChange = { newsTitle = it },
                    placeholder = { Text(text = "News Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                    value = newsContent,
                    onValueChange = { newsContent = it },
                    placeholder = { Text(text = "News Content") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    enabled = !insertState.value.isLoading(),
                    onClick = {
                        viewModel.insertNewNews(
                            NewsEntity(
                                authorUsername = LocalUser.username ?: "",
                                title = newsTitle,
                                content = newsContent,
                                authorId = LocalUser.uuid,
                                headerImgUrl = displayNewsUrl
                            )
                        )
                    }
                ) {
                    Text(text = "Publish")
                }
                Spacer(modifier = Modifier.height(12.dp))
                insertState.value.DisplayResult(
                    onLoading = { CircularProgressIndicator() },
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Success mengupload berita!",
                            Toast.LENGTH_SHORT
                        ).show(); navigator.navigateUp()
                    },
                    onError = {
                        ErrorBox(message = it)
                    }
                )
            }
        }
    }


}