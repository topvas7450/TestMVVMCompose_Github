package com.example.githubtest.ui.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtest.data.remote.api.httpClient
import com.example.githubtest.data.remote.api.user
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.headersOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//import timber.log.Timber

data class LoginUiState(
    var isLoading: Boolean = false,
    var success: Boolean = false,
    var emailState: String? = null,
    var passwordState: String? = null,
)

data class TextFieldState(
    val text :  String = "",
    val error: String? = null
)

@Serializable
data class User(
    val login: String,
    val id: Long,
    @SerialName("avatar_url")
    val avatarUrl: String,
)

class LoginViewModel : ViewModel() {
    // use stateflow
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // not flow
//    private var _loginState  = mutableStateOf(LoginUiState())
//    val loginState: State<LoginUiState> = _loginState


    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    fun setPassword(value:String){
        _passwordState.value = passwordState.value.copy(text = value)
        Napier.i("_passwordState:${_passwordState}")
    }

    fun resetLoginUiState() {
        _uiState.update {
            it.copy(emailState = null, passwordState = null)
        }
        _passwordState.value.copy(error = "密码不能为空")
    }

    fun login(username: String, password: String) {
        resetLoginUiState()
        if (username.isBlank()) {
            _uiState.update {
                it.copy(emailState = "用户名不能为空")
            }
            return
        }
        if (password.isBlank()) {
            _uiState.update {
                it.copy(passwordState = "密码不能为空")
            }
            _passwordState.value.copy(error = "密码不能为空")
            Napier.i("password.isBlank")
            return
        }
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            user()
//            val response: HttpResponse = httpClient.get("https://api.github.com/users/$userName")
//            if (response.status.value in 200..299) {
////                val stringBody: String = response.body()
////                val byteArrayBody: ByteArray = response.body()
//                val user: User = response.body()
//                Napier.i("${user}")
//                Napier.i("Successful response!")
//            }
//            val tmp: HttpResponse = httpClient.get("https://api.github.com/users/$userName/starred") {
//                headers {
//                    append("Accept", "application/vnd.github+json")
//                    append("Authorization", "Bear $gToken")
//                    append("X-GitHub-Api-Version", "2022-11-28")
//                }
//            }
//            if (tmp.status.value in 200..299) {
////                val stringBody: String = response.body()
////                val byteArrayBody: ByteArray = response.body()
////                val user: User = response.body()
////                Timber.i("${user}")
//                Napier.i("tmp Successful response!")
//            }
//            val response = post<Login> {
//                setUrl("user/login")
//                putParam("username", username)
//                putParam("password", password)
//            }
//            WanHelper.setUser(response.data)
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    success = response.errorCode == "0",
//                    message = response.errorMsg
//                )
//            }
        }
    }

}