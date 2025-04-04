package com.hderbali.profile.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hderbali.model.User
import com.hderbali.ui.theme.AppDimensions

@Composable
fun ProfileStats(user: User) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(
            value = user.postCount,
            label = "Posts"
        )

        Box(
            modifier = Modifier.Companion
                .height(36.dp)
                .width(1.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        StatItem(
            value = user.followerCount,
            label = "Subscribed"
        )

        Box(
            modifier = Modifier.Companion
                .height(36.dp)
                .width(1.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        StatItem(
            value = user.followingCount,
            label = "Subscriptions"
        )
    }
}