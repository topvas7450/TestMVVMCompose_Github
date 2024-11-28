package com.example.githubtest.data.remote.api.resp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchResponseOwner(
    @SerialName("login") val login: String = "",
    @SerialName("id") val id: Int = 0,
    @SerialName("avatar_url") val avatarUrl: String = "",
)
