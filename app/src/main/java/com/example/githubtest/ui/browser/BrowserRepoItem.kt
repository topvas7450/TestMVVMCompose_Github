package com.example.githubtest.ui.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubtest.R
import com.example.githubtest.domain.model.RepoItem
import com.example.githubtest.ui.theme.GithubTestTheme
import com.example.githubtest.domain.model.Owner
import kotlinx.datetime.Clock

@Composable
fun BrowserRepoItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    item: RepoItem,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier.padding(5.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 3.dp,
        ),
        shape = RoundedCornerShape(size = 20.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(92.dp)
                    .clip(RoundedCornerShape(size = 20.dp))
                    .background(Color.White),
                model = remember(context, item.owner.avatar) {
                    ImageRequest.Builder(context)
                        .data(item.owner.avatar)
                        .crossfade(true)
                        .build()
                },
                placeholder = painterResource(R.drawable.icons8_github_500),
                contentDescription = "Avatar",
                contentScale = ContentScale.FillBounds,
            )

            Column(
                modifier = Modifier.weight(1f)
                    .padding(start = 5.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    item.fullName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    item.repoDescription ?: "",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun GithubRepoItemRowPreview() {
    GithubTestTheme {
        BrowserRepoItem(
            item = RepoItem(
                id = 3376941,
                fullName = "topvas7450",
                language = null,
                starCount = 1000,
                name = "topvas7450",
                repoDescription = null,
                htmlUrl = "https://github.com/topvas7450",
                owner = Owner(
                    id = 3376941,
                    username = "topvas7450",
                    avatar = "https://avatars.githubusercontent.com/u/3376941?v=4",
                ),
                updatedAt = Clock.System.now(),
            ),
//            decimalFormat = remember { StableWrapper(DecimalFormat("#,###")) },
        )
    }
}