package com.example.githubtest.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.githubtest.R
//import timber.log.Timber
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.aakira.napier.Napier

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
//    viewModel: LoginViewModel = LoginViewModel() // LoginViewModel會被重建
    viewModel: LoginViewModel = viewModel()
) {
    Napier.i("loginScreen...")
    var usernameText by rememberSaveable { mutableStateOf("") }
//    var pwdState by rememberSaveable { mutableStateOf("") }
    val passwordState by viewModel.passwordState
    // use stateflow
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    val loginState = viewModel.loginState.value
//    val scaffoldState = rememberScaffoldState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
//            verticalArrangement = Arrangement.SpaceBetween, // 將子元素分佈在頂部和底部
            horizontalAlignment = Alignment.CenterHorizontally // 將子元素水平對齊
        ) {
            Napier.i("Column...")
            Image(
                painter = painterResource(R.drawable.icons8_github_500),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = usernameText,
                onValueChange = {
                    usernameText = it
                },
                placeholder = {
                    Text(text = "Enter Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                isError = uiState.emailState != null
            )
            if (uiState.emailState != null) {
                Text(
                    text = uiState.emailState ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordState.text,
                onValueChange = {
                    Napier.d("it:$it")
                    viewModel.setPassword(it)
//                    pwdState = it
                    Napier.d("viewModel:$viewModel passwordState:${viewModel.passwordState.value}")
                },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordState.error != null,
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            Napier.d("viewModel:${viewModel} ${passwordState}  passwordState:${viewModel.passwordState.value}")
            if (passwordState.error != "") {
                Text(
                    text = passwordState.error ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.login(usernameText, passwordState.text)
                },
                shape = RoundedCornerShape(16),
                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = PurpleBg,
//                    contentColor = Color.White
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Log In",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )

            }
        }
    }
}