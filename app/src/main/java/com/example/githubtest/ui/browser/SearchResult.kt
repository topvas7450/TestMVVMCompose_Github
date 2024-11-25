package com.example.githubtest.ui.browser

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.githubtest.domain.model.RepoItem

@Composable
fun SearchResult(
    datas: List<RepoItem>,
) {
// Show the data
    LazyColumn {
        items(datas.size) { index ->
            BrowserRepoItem(item = datas[index])
        }
    }
}