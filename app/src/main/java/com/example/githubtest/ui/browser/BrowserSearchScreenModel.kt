package com.example.githubtest.ui.browser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtest.data.remote.api.searchUserRepos
import com.example.githubtest.domain.model.Owner
import com.example.githubtest.domain.model.RepoItem
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = true,
    val success: Boolean = false,
)

data class BrowserResultState(
    val repos: List<RepoItem> = listOf()
)

class BrowserSearchScreenModel: ViewModel() {
    // collectAsStateWithLifecycle , collectAsState
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _browserResultState = MutableStateFlow(BrowserResultState())
    val browserResultState: StateFlow<BrowserResultState> = _browserResultState.asStateFlow()

    fun loading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    fun searchUserRepos(userName: String) {
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
}