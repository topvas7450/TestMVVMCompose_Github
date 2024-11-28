package com.example.githubtest.data.remote.api.resp

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchUserResponse(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    @SerialName("items") val items: List<GithubSearchUserResponseItem>? = null,
) {

}

@Serializable
data class GithubSearchUserResponseItem(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("html_url") val htmlUrl: String,
)
