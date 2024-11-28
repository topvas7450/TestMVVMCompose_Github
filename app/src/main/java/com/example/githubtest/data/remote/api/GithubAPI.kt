package com.example.githubtest.data.remote.api

import com.example.githubtest.data.remote.api.resp.GithubSearchReposResponse
import com.example.githubtest.data.remote.api.resp.GithubSearchUserResponse
import com.example.githubtest.data.remote.api.resp.GithubUserStarredReposResponse
import com.example.githubtest.ui.login.User
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLBuilder
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

//import timber.log.Timber

val gToken = "xxx"
val baseUrl = "https://api.github.com/"

val httpClient: HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
//        filter { request ->
//            request.url.host.contains("ktor.io")
//        }
    }
    engine {
        // this: HttpClientEngineConfig
//        threadsCount = 4
        pipelining = true
    }
}

suspend fun user(userName: String) {

    val response: HttpResponse = httpClient.get("https://api.github.com/users/$userName")
    if (response.status.value in 200..299) {
//                val stringBody: String = response.body()
//                val byteArrayBody: ByteArray = response.body()
        val user: User = response.body()
        Napier.i("${user}")
        Napier.i("Successful response!")
    }
    val tmp: HttpResponse = httpClient.get("https://api.github.com/users/$userName/starred") {
        headers {
            append("Accept", "application/vnd.github+json")
            append("Authorization", "Bear $gToken")
            append("X-GitHub-Api-Version", "2022-11-28")
        }
    }
    if (tmp.status.value in 200..299) {
                val stringBody: String = tmp.body()
//                val byteArrayBody: ByteArray = response.body()
        Napier.i("${stringBody}")
        Napier.i("Successful response!")
    }
}

suspend fun starred(userName: String) {
    val tmp: HttpResponse = httpClient.get("https://api.github.com/users/$userName/starred") {
//        headers {
//            append("Accept", "application/vnd.github+json")
//            append("Authorization", "Bear $gToken")
//            append("X-GitHub-Api-Version", "2022-11-28")
//        }
    }
    if (tmp.status.value in 200..299) {
//                val stringBody: String = response.body()
//                val byteArrayBody: ByteArray = response.body()
//                val user: User = response.body()
//                Timber.i("${user}")
        Napier.i("tmp Successful response!")
    }
}
// /users/{username}/repos

suspend fun userRepos(userName: String): GithubSearchReposResponse? {

    val response: HttpResponse = httpClient.get("https://api.github.com/users/$userName/repos") {
        headers {
            append("Accept", "application/vnd.github+json")
            append("Authorization", "Bear $gToken")
            append("X-GitHub-Api-Version", "2022-11-28")
        }
    }

        if (response.status.value in 200..299) {
                val stringBody: String = response.body()
//                val byteArrayBody: ByteArray = response.body()
//        val user: List<RepoItemsSearchResponse> = response.body()
        Napier.i("${stringBody}")
        Napier.i("Successful response!")
    }

    return null
}


// https://blog.csdn.net/Next_Second/article/details/78238328
suspend fun searchUserRepos(userName: String, page: Int) = withContext(Dispatchers.IO) {
    httpClient.get(
        URLBuilder(baseUrl)
            .apply {
                path("search/repositories")
                parameters.append("q", "user:$userName")
                parameters.append("page", page.toString())
            }
            .build(),
    ).body<GithubSearchReposResponse>()
}

suspend fun searchUserStarredRepos(userName: String, page: Int) = withContext(Dispatchers.IO) {
    httpClient.get(
        URLBuilder(baseUrl)
            .apply {
                path("users/$userName/starred")
//                parameters.append("q", "user:$userName")
                parameters.append("page", page.toString())
            }
            .build(),
    ).body<GithubUserStarredReposResponse>()
}

// https://github.com/github/docs/blob/main/content/search-github/searching-on-github/searching-users.md
// https://docs.github.com/en/search-github/searching-on-github/searching-users
// https://api.github.com/search/users?q=topvas7450+in%3Afullname&type=Users
suspend fun searchUser(userName: String, page: Int = 1) = withContext(Dispatchers.IO) {
    httpClient.get(
        URLBuilder(baseUrl)
            .apply {
                path("search/users")
                // 參數'+'
                encodedParameters.append("q", "$userName+in%3Afullname")
                parameters.append("page", page.toString())
            }
            .build(),
    ).body<GithubSearchUserResponse>()
}