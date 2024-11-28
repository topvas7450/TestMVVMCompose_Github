package com.example.githubtest.domain.model

import kotlinx.datetime.Instant

data class RepoItem(
  val id: Int,
  val fullName: String,
  val language: String? = null,
  val starCount: Int? = null,
  val name: String? = null,
  val repoDescription: String? = null,
  val htmlUrl: String,
  val owner: Owner = Owner(),
  val updatedAt: Instant? = null,
)
