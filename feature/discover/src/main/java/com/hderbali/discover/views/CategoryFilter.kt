package com.hderbali.discover.views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hderbali.discover.DiscoverCategory
import com.hderbali.ui.theme.AppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilter(
    selectedCategory: DiscoverCategory,
    onCategorySelect: (DiscoverCategory) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = AppDimensions.spacing8, vertical = AppDimensions.spacing8)
    ) {
        DiscoverCategory.values().forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelect(category) },
                label = {
                    Text(
                        text = when (category) {
                            DiscoverCategory.ALL -> "All"
                            DiscoverCategory.PHOTOS -> "Photos"
                            DiscoverCategory.VIDEOS -> "Videos"
                            DiscoverCategory.MUSIC -> "Music"
                            DiscoverCategory.TRENDING -> "Trends"
                        }
                    )
                },
                modifier = Modifier.Companion.padding(horizontal = AppDimensions.spacing4),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}