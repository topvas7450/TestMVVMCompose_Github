package com.example.githubtest.data.remote.api.resp

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchReposResponse(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    @SerialName("items") val items: List<GithubSearchResponseItem>? = null,
)
