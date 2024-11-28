package com.example.githubtest.ui.browser

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtest.data.remote.api.searchUser
import com.example.githubtest.data.remote.api.searchUserRepos
import com.example.githubtest.data.remote.api.searchUserStarredRepos
import com.example.githubtest.domain.model.Owner
import com.example.githubtest.domain.model.RepoItem
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = true,
    val success: Boolean = false,
    val browserType: SearchType = SearchType.Repos
)

data class BrowserResultState(
    val repos: List<RepoItem> = listOf()
)

enum class SearchType(val showText: String) {
    Repos("Repos"),
    Starred("Starred"),
    User("user")
}

class BrowserSearchScreenModel: ViewModel() {
    // collectAsStateWithLifecycle只是用android , 各種平台的程式碼使用collectAsState
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _queryStr = mutableStateOf("")

    private val _browserResultState = MutableStateFlow(BrowserResultState())
    val browserResultState: StateFlow<BrowserResultState> = _browserResultState.asStateFlow()

    fun loading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun onSearchStrChange(str: String) {
        _queryStr.value = str
    }

    fun isSearchTypeSelected(searchType: SearchType): Boolean {
        return uiState.value.browserType == searchType
    }

    fun searchTypeSelected(searchType: SearchType) {
        _uiState.update {
            it.copy(browserType = searchType)
        }
        if (_queryStr.value.isNotEmpty()) {
            search(_queryStr.value)
        }
    }

    fun search(queryStr: String) {
        viewModelScope.launch {
            when (uiState.value.browserType) {
                SearchType.Repos -> searchUserRepos(queryStr)
                SearchType.Starred -> searchStarredRepos(queryStr)
                SearchType.User -> searchUsers(queryStr)
            }
        }
    }

    private fun searchUserRepos(userName: String) {
        loading(true)
        viewModelScope.launch {
            val res = searchUserRepos(userName, 1)
//            val res = userRepos(repoName)
            Napier.d("$res")

            val list = res?.items?.map {
                RepoItem(
                    id = it.id,
                    fullName = it.fullName,
                    language = it.language,
                    starCount = it.stargazersCount,
                    name = it.name,
                    repoDescription = it.description,
                    htmlUrl = it.htmlUrl,
                    owner = Owner(it.owner.id, it.owner.login, it.owner.avatarUrl),
                    updatedAt = it.updatedAt
                )
            }
            list?.apply {
                _browserResultState.update {
                    it.copy(repos = this)
                }
            }
            loading(false)
        }

    }

    private fun searchStarredRepos(userName: String) {
        loading(true)
        viewModelScope.launch {
            val res = searchUserStarredRepos(userName, 1)
//            val res = userRepos(repoName)
            Napier.d("$res")

            val list = res?.map {
                RepoItem(
                    id = it.id,
                    fullName = it.fullName,
                    language = it.language,
                    starCount = it.stargazersCount,
                    name = it.name,
                    repoDescription = it.description,
                    htmlUrl = it.htmlUrl,
                    owner = Owner(it.owner.id, it.owner.login, it.owner.avatarUrl),
                    updatedAt = it.updatedAt
                )
            }
            list?.apply {
                _browserResultState.update {
                    it.copy(repos = this)
                }
            }
            loading(false)
        }
    }

    private fun searchUsers(userName: String) {
        loading(true)
        viewModelScope.launch {
            val res = searchUser(userName, 1)
//            val res = userRepos(repoName)
            Napier.d("$res")

            val list = res?.items?.map {
                RepoItem(
                    id = it.id,
                    fullName = it.login,
                    htmlUrl = it.htmlUrl,
                    owner = Owner(it.id, it.login, it.avatarUrl)
                )
            }
            list?.apply {
                _browserResultState.update {
                    it.copy(repos = this)
                }
            }
            loading(false)
        }
    }
}