package com.example.githubtest.data.remote.api.resp

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchReposResponse(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    @SerialName("items") val items: List<Item>? = null,
) {
  @Serializable
  data class Item(
      @SerialName("id") val id: Int,
      @SerialName("name") val name: String,
      @SerialName("full_name") val fullName: String,
      @SerialName("owner") val owner: Owner,
      @SerialName("html_url") val htmlUrl: String,
      @SerialName("description") val description: String?,
      @SerialName("updated_at") val updatedAt: Instant,
      @SerialName("stargazers_count") val stargazersCount: Int,
      @SerialName("language") val language: String?,
  ) {
    @Serializable
    data class Owner(
      @SerialName("login") val login: String,
      @SerialName("id") val id: Int,
      @SerialName("avatar_url") val avatarUrl: String,
    )
  }
}
