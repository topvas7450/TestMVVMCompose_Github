package com.example.githubtest.ui.browser

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchStr: String,
    onSearchStrChange: (String) -> Unit,
    onDoSearch: (String) -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    TextField(
        modifier = modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        ),
        value = searchStr,
        onValueChange = onSearchStrChange,
//        onValueChange = {  },
        label = { Text("Search User repo...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = searchStr.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                IconButton(
                    onClick = { onSearchStrChange("") },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "Clear",
                    )
                }
            }
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                localFocusManager.clearFocus()
                onDoSearch.invoke(searchStr)
            },
        ),
    )
}

@Preview
@Composable
private fun SearchTermBoxPreview() {
    var input by remember { mutableStateOf("Test") }
    SearchBox(
        onSearchStrChange = { input = it },
        searchStr = input,
        onDoSearch = {}
    )
}