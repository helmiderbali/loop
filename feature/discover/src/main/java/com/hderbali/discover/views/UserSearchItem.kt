package com.hderbali.discover.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.User
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme

@Composable
fun UserSearchItem(
    user: User,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Box(
            modifier = Modifier.Companion
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Companion.Center
        ) {
            if (user.profilePicUrl.isNotEmpty()) {
                val imageUrl = user.profilePicUrl + "?w=800"

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Photo de profil de ${user.displayName}",
                    contentScale = ContentScale.Companion.Crop,
                    modifier = Modifier.Companion.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.Companion.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing12))

        Column(modifier = Modifier.Companion.weight(1f)) {
            Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                Text(
                    text = user.displayName,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Companion.Ellipsis
                )

                if (user.isVerified) {
                    Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = "Compte vérifié",
                        tint = ExtendedTheme.verifiedColor,
                        modifier = Modifier.Companion.size(16.dp)
                    )
                }
            }

            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Companion.Ellipsis
            )
        }
    }
}