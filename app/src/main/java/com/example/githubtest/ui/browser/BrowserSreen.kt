package com.example.githubtest.ui.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    var queryStr by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val browserResultState by viewModel.browserResultState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(uiState.isLoading) {
            Napier.i("LaunchedEffect... ${uiState.isLoading}")
            if (uiState.isLoading) {
                // Perform a long-running operation, such as fetching data from a network
//                delay(1000)
//                val newData = fetchData()
                // Update the state with the new data
//                data.value = newData
//                viewModel.loading(false)
                viewModel.searchUserRepos("topvas7450")
            }
        }
        SearchBox(
            modifier = Modifier.fillMaxWidth(),
            searchStr = queryStr,
            onSearchStrChange = { queryStr = it },
            onDoSearch = {
                viewModel.searchUserRepos(it)
                Napier.d(message = it, tag = "onDoSearch")
            }
        )
        if (uiState.isLoading) {
            CircularProgressIndicator()
            return@Column
        }
        // type: user, repos, start
//        Text("result")

        SearchResult(browserResultState.repos)
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
                    searchStr = "term",
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