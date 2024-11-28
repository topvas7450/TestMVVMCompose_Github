package com.example.githubtest.data.remote.api.resp

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//data class GithubUserStarredReposResponse(
//    @SerialName("items") val items: List<GithubSearchResponseItem>? = null,
//    ) {
//    @Serializable
//    data class Item(
//        @SerialName("id") val id: Int,
//        @SerialName("name") val name: String,
//        @SerialName("full_name") val fullName: String,
//        @SerialName("html_url") val htmlUrl: String,
//        @SerialName("description") val description: String?,
//        @SerialName("stargazers_count") val stargazersCount: Int,
//        @SerialName("language") val language: String?,
//        @SerialName("updated_at") val updatedAt: Instant,
//    )
//}

typealias GithubUserStarredReposResponse = List<GithubSearchResponseItem>