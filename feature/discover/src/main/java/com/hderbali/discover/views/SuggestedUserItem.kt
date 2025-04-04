package com.hderbali.discover.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.User
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.ExtendedTheme

@Composable
fun SuggestedUserItem(
    user: User,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .width(80.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.Companion
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
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
                    modifier = Modifier.Companion.size(32.dp)
                )
            }

            if (user.isVerified) {
                Box(
                    modifier = Modifier.Companion
                        .align(Alignment.Companion.BottomEnd)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(2.dp),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = "Compte vérifié",
                        tint = ExtendedTheme.verifiedColor,
                        modifier = Modifier.Companion.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing4))

        Text(
            text = user.username,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Companion.Ellipsis,
            textAlign = TextAlign.Companion.Center
        )
    }
}