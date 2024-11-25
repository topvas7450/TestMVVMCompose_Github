package com.example.githubtest.domain.model

import kotlinx.datetime.Instant

data class RepoItem(
  val id: Int,
  val fullName: String,
  val language: String?,
  val starCount: Int,
  val name: String,
  val repoDescription: String?,
  val htmlUrl: String,
  val owner: Owner,
  val updatedAt: Instant,
)
