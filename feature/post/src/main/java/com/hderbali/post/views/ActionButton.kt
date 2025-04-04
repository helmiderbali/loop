package com.hderbali.post.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.hderbali.ui.theme.AppDimensions
import com.yourapp.core.ui.theme.AppTextStyles

@Composable
fun ActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.Companion
            .clickable(onClick = onClick)
            .padding(horizontal = AppDimensions.spacing8, vertical = AppDimensions.spacing4),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.Companion.size(24.dp)
        )

        if (text.isNotEmpty()) {
            Spacer(modifier = Modifier.Companion.width(AppDimensions.spacing4))
            Text(
                text = text,
                style = AppTextStyles.Count,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}