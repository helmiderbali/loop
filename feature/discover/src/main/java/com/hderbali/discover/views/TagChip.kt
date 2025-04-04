package com.hderbali.discover.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes

@Composable
fun TagChip(
    tag: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.Companion.clickable(onClick = onClick),
        shape = AppSpecificShapes.ChipShape,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = "#$tag",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.Companion.padding(
                horizontal = AppDimensions.spacing12,
                vertical = AppDimensions.spacing8
            )
        )
    }
}