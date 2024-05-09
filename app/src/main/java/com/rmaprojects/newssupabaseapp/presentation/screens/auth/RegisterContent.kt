package com.rmaprojects.newssupabaseapp.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.presentation.components.common.ErrorBox

@Composable
fun RegisterContent(
    username: String,
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    registerState: State<ResponseState<LocalUser>>,
    onSuccessRegistration: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "New with us?",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Register to join with us!",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = onUsernameChanged,
            placeholder = {
                Text(text = "Username")
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChanged,
            placeholder = {
                Text(text = "Email")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChanged,
            placeholder = {
                Text(text = "Password")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRegisterClick,
            enabled = !registerState.value.isLoading()
        ) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onLoginClick,
            enabled = !registerState.value.isLoading()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(12.dp))
        registerState.value.DisplayResult(
            onLoading = {
                CircularProgressIndicator()
            },
            onSuccess = {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                onSuccessRegistration()
            },
            onError = {
                ErrorBox(message = it)
            }
        )
    }

}