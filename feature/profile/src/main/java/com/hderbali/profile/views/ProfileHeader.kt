package com.hderbali.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hderbali.model.User
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes
import com.hderbali.ui.theme.ExtendedTheme

@Composable
fun ProfileHeader(
    user: User,
    isCurrentUser: Boolean,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    onEditProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.Companion
                .size(AppDimensions.profilePictureLarge)
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
                    modifier = Modifier.Companion.size(AppDimensions.profilePictureLarge / 2)
                )
            }
        }

        Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing12))

        Row(
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Companion.Bold
            )

            if (user.isVerified) {
                Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
                Icon(
                    imageVector = Icons.Outlined.Verified,
                    contentDescription = "Compte vérifié",
                    tint = ExtendedTheme.verifiedColor,
                    modifier = Modifier.Companion.size(20.dp)
                )
            }
        }

        Text(
            text = "@${user.username}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.Companion.height(AppDimensions.spacing16))

        if (isCurrentUser) {
            OutlinedButton(
                onClick = onEditProfileClick,
                modifier = Modifier.Companion.fillMaxWidth(0.8f),
                contentPadding = PaddingValues(
                    horizontal = AppDimensions.spacing16,
                    vertical = AppDimensions.spacing8
                ),
                shape = AppSpecificShapes.ButtonShape
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.Companion.size(20.dp)
                )
                Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing8))
                Text(text = "Update profile")
            }
        } else {
            Button(
                onClick = onFollowClick,
                modifier = Modifier.Companion.fillMaxWidth(0.8f),
                contentPadding = PaddingValues(
                    horizontal = AppDimensions.spacing16,
                    vertical = AppDimensions.spacing8
                ),
                shape = AppSpecificShapes.ButtonShape,
                colors = if (isFollowing) {
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    ButtonDefaults.buttonColors()
                }
            ) {
                Text(
                    text = if (isFollowing) "Abonné" else "Suivre",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}