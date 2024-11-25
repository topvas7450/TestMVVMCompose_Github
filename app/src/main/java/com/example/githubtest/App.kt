package com.example.githubtest

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubtest.ui.Routes
import com.example.githubtest.ui.browser.BrowserSearchScreen
import com.example.githubtest.ui.login.LoginScreen
import com.example.githubtest.ui.theme.GithubTestTheme
import io.github.aakira.napier.Napier

@Composable
fun App() {
    GithubTestTheme {
        Napier.i("GithubTestTheme...")
        Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = rememberNavController(),
                startDestination = Routes.BrowserSearch.route,
                modifier = Modifier.padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            ) {
                composable(Routes.BrowserSearch.route) {
                    BrowserSearchScreen()
                }
                composable(Routes.LoginTest.route) {
                    LoginScreen()
                }
            }
        }
    }
}