package com.hderbali.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

// Extended shape definitions for specific UI components
object AppSpecificShapes {
    val CardShape = RoundedCornerShape(16.dp)
    val ButtonShape = RoundedCornerShape(24.dp)
    val ImageShape = RoundedCornerShape(12.dp)
    val TopSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val BottomSheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    val ProfilePictureSmall = RoundedCornerShape(percent = 50) // Circle
    val ProfilePictureLarge = RoundedCornerShape(percent = 50) // Circle
    val TextFieldShape = RoundedCornerShape(12.dp)
    val ChipShape = RoundedCornerShape(16.dp)
}

// Dimensions for consistent spacing throughout the app
object AppDimensions {
    // Screen margins
    val screenMargin = 16.dp
    val contentMargin = 16.dp

    // Spacing
    val spacing0 = 0.dp
    val spacing2 = 2.dp
    val spacing4 = 4.dp
    val spacing8 = 8.dp
    val spacing12 = 12.dp
    val spacing16 = 16.dp
    val spacing20 = 20.dp
    val spacing24 = 24.dp
    val spacing32 = 32.dp
    val spacing40 = 40.dp
    val spacing48 = 48.dp
    val spacing56 = 56.dp
    val spacing64 = 64.dp

    // Component specific
    val cardElevation = 2.dp
    val feedItemPadding = 12.dp
    val profilePictureSmall = 36.dp
    val profilePictureMedium = 48.dp
    val profilePictureLarge = 84.dp
    val postImageHeight = 280.dp
    val iconSize = 24.dp
    val iconSizeSmall = 16.dp
    val iconSizeLarge = 32.dp
    val dividerThickness = 1.dp
    val bottomBarHeight = 56.dp
    val topBarHeight = 56.dp
    val buttonHeight = 48.dp
    val buttonHeightSmall = 32.dp
    val fabSize = 56.dp
    val maxContentWidth = 720.dp
}

// Elevation values
object AppElevation {
    val level0 = 0.dp
    val level1 = 1.dp
    val level2 = 3.dp
    val level3 = 6.dp
    val level4 = 8.dp
    val level5 = 12.dp
}