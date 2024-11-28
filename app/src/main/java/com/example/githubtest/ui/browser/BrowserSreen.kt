package com.example.githubtest.ui.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay

@Composable
fun BrowserSearchScreen(
    viewModel: BrowserSearchScreenModel = viewModel()
) {
    val isInitialized = remember { mutableStateOf(false) }
    var queryStr by remember { mutableStateOf("topvas7450") }
    val uiState by viewModel.uiState.collectAsState()
    val browserResultState by viewModel.browserResultState.collectAsState()

    SideEffect {
        Napier.i("SideEffect...isInitialized:${isInitialized.value}")
        if (!isInitialized.value) {
            viewModel.search(queryStr)
            viewModel.onSearchStrChange(queryStr)

            isInitialized.value = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(uiState.isLoading) {
            Napier.i("LaunchedEffect... ${uiState.isLoading}")
            if (uiState.isLoading) {
                Napier.i("LaunchedEffect...in:${uiState.isLoading}")
                // Perform a long-running operation, such as fetching data from a network
//                delay(1000)
//                val newData = fetchData()
                // Update the state with the new data
//                data.value = newData
//                viewModel.loading(false)

            }
        }
        Napier.d("viewModel:$viewModel")
        SearchBox(
            modifier = Modifier.fillMaxWidth(),
            searchStr = queryStr,
            onSearchStrChange = {
                queryStr = it
                viewModel.onSearchStrChange(it)
            },
            onDoSearch = {
                viewModel.search(it)
                Napier.d(message = it, tag = "onDoSearch")
            }
        )
        if (uiState.isLoading) {
            CircularProgressIndicator()
            return@Column
        }
        // type: user, repos, start
        SearchTypeSelectorV2(
            selectedSearchType = uiState.browserType,
            onItemClick = { selectedSearchType ->
                viewModel.searchTypeSelected(selectedSearchType)
            }
        )

        SearchResult(browserResultState.repos)
    }
}

// TODO 點擊分類時會進行搜尋，但在api回來前點及其他分類的處理?
// * cancel進行中api emit下一個搜尋,
// * block ui直到api回來或timeout

// 測試狀態提升或是viewModel共用(hilt, remember(compositionalLocal), provider)
// 傳入viewModel會減少composable的復用性，使與view耦合較深
@Composable
fun SearchTypeSelector(
    viewModel: BrowserSearchScreenModel = viewModel()
) {
    Napier.d("viewModel:$viewModel")
    val uiState by viewModel.uiState.collectAsState()

    Row(
       modifier = Modifier.fillMaxWidth()
           .padding(start = 16.dp, end = 16.dp)
    ) {
        Button(
            onClick = {
                viewModel.searchTypeSelected(SearchType.Repos)
            },
            colors = ButtonDefaults.buttonColors(
                // 所有button都這樣寫的話不會recompose，顏色沒有改變
                containerColor = if(viewModel.isSearchTypeSelected(SearchType.Repos)) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.Repos.showText)
        }
        Spacer(modifier = Modifier.padding(1.dp))
        Button(
            onClick = {
                viewModel.searchTypeSelected(SearchType.Starred)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(uiState.browserType == SearchType.Starred) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.Starred.showText)
        }
        Spacer(modifier = Modifier.padding(1.dp))
        Button(
            onClick = {
                viewModel.searchTypeSelected(SearchType.User)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(uiState.browserType == SearchType.User) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.User.showText)
        }
    }
}

/**
 * V2將composable的狀態提升
 */
@Composable
fun SearchTypeSelectorV2(
    selectedSearchType: SearchType,
    onItemClick: (SearchType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Button(
            onClick = {
                onItemClick(SearchType.Repos)
            },
            colors = ButtonDefaults.buttonColors(
                // 所有button都這樣寫的話不會recompose，顏色沒有改變
                containerColor = if(selectedSearchType == SearchType.Repos) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.Repos.showText)
        }
        Spacer(modifier = Modifier.padding(1.dp))
        Button(
            onClick = {
                onItemClick(SearchType.Starred)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(selectedSearchType == SearchType.Starred) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.Starred.showText)
        }
        Spacer(modifier = Modifier.padding(1.dp))
        Button(
            onClick = {
                onItemClick(SearchType.User)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if(selectedSearchType == SearchType.User) Color(0xFFFF9800) else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(maxLines = 1, text = SearchType.User.showText)
        }
    }
}

@Preview
@Composable
private fun BrowserSearchScreenPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBox(
            modifier = Modifier.fillMaxWidth(),
            searchStr = "term",
            onSearchStrChange = { },
            onDoSearch = { }
        )
        SearchTypeSelector()
    }
}

@Preview
@Composable
private fun BrowserSearchBottomPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().fillMaxHeight()) {
            Text("bottom left", modifier = Modifier.align(Alignment.BottomStart))
        }
        Column(
            modifier = Modifier.align(Alignment.TopStart) // Align column to top start
        ) {
            Text("I am at the top left")
        }
        Button(
            onClick = { /* Do something */},
            modifier = Modifier.align(Alignment.BottomStart) // Align button to bottom start
        ) {

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TestBottomLayout() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize() // fill the entire window
            .background(Color.DarkGray),
        bottomBar = {
            Box(
                modifier = Modifier
//                .align(Alignment.BottomEnd)
                    .background(Color.White)
                    .padding(0.dp) // normal 16dp of padding for FABs
//                    .navigationBarsPadding() // padding for navigation bar
                    .imePadding(), // padding for when IME appears
            ) {
//                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                SearchBox(
                    modifier = Modifier.fillMaxWidth(),
                    searchStr = "repos name",
                    onSearchStrChange = { },
                    onDoSearch = { }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize() // fill the entire window
                .padding(innerPadding)
                .imePadding() // padding for the bottom for the IME
                .imeNestedScroll(), // scroll IME at the bottom
            content = {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Green)
                ) {
                    Box(Modifier.fillMaxSize().fillMaxHeight()) {
                        Text("bottom left", modifier = Modifier.align(Alignment.BottomStart))
                    }
                    Column(
                        modifier = Modifier.align(Alignment.TopStart) // Align column to top start
                    ) {
                        Text("I am at the top left")
                    }
                    Button(
                        onClick = { /* Do something */},
                        modifier = Modifier.align(Alignment.BottomStart) // Align button to bottom start
                    ) {

                    }
                }
            }
        )

    }
}


// Simulate a network call by suspending the coroutine for 2 seconds
private suspend fun fetchData(): List<String> {
    Napier.i("fetchData..")
    // Simulate a network delay
    delay(2000)
    return listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5",)
}