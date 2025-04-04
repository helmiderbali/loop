package com.hderbali.profile.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.hderbali.ui.common.utils.formatCount

@Composable
fun StatItem(
    value: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Text(
            text = formatCount(value),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Companion.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
