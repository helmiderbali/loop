package com.hderbali.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Light Theme Colors
val LightPrimary = Color(0xFF4169E1)         // Royal Blue
val LightOnPrimary = Color(0xFFFFFFFF)       // White
val LightPrimaryContainer = Color(0xFFDDE1FF) // Light Blue
val LightOnPrimaryContainer = Color(0xFF001356) // Dark Blue

val LightSecondary = Color(0xFF6B50A6)       // Purple
val LightOnSecondary = Color(0xFFFFFFFF)     // White
val LightSecondaryContainer = Color(0xFFEADDFF) // Light Purple
val LightOnSecondaryContainer = Color(0xFF25005A) // Dark Purple

val LightTertiary = Color(0xFF00A86B)        // Green
val LightOnTertiary = Color(0xFFFFFFFF)      // White
val LightTertiaryContainer = Color(0xFFADF5D3) // Light Green
val LightOnTertiaryContainer = Color(0xFF002116) // Dark Green

val LightError = Color(0xFFBA1A1A)          // Red
val LightOnError = Color(0xFFFFFFFF)        // White
val LightErrorContainer = Color(0xFFFFDAD6) // Light Red
val LightOnErrorContainer = Color(0xFF410002) // Dark Red

val LightBackground = Color(0xFFF9F9F9)     // Off White
val LightOnBackground = Color(0xFF1A1C1E)   // Almost Black
val LightSurface = Color(0xFFFFFFFF)        // White
val LightOnSurface = Color(0xFF1A1C1E)      // Almost Black
val LightSurfaceVariant = Color(0xFFE1E2EC) // Light Gray
val LightOnSurfaceVariant = Color(0xFF44474F) // Gray

val LightOutline = Color(0xFF74777F)        // Medium Gray
val LightOutlineVariant = Color(0xFFC4C6D0) // Light Gray

// Dark Theme Colors
val DarkPrimary = Color(0xFF98BCFF)         // Light Blue
val DarkOnPrimary = Color(0xFF002882)       // Dark Blue
val DarkPrimaryContainer = Color(0xFF0A3EBD) // Medium Blue
val DarkOnPrimaryContainer = Color(0xFFDDE1FF) // Very Light Blue

val DarkSecondary = Color(0xFFD3BBFF)       // Light Purple
val DarkOnSecondary = Color(0xFF3C1D80)     // Dark Purple
val DarkSecondaryContainer = Color(0xFF53398F) // Medium Purple
val DarkOnSecondaryContainer = Color(0xFFEADDFF) // Very Light Purple

val DarkTertiary = Color(0xFF8AD9B8)        // Light Green
val DarkOnTertiary = Color(0xFF003828)      // Dark Green
val DarkTertiaryContainer = Color(0xFF005138) // Medium Green
val DarkOnTertiaryContainer = Color(0xFFADF5D3) // Very Light Green

val DarkError = Color(0xFFFFB4AB)          // Light Red
val DarkOnError = Color(0xFF690005)        // Dark Red
val DarkErrorContainer = Color(0xFF93000A) // Medium Red
val DarkOnErrorContainer = Color(0xFFFFDAD6) // Very Light Red

val DarkBackground = Color(0xFF1A1C1E)     // Dark Gray
val DarkOnBackground = Color(0xFFE2E2E6)   // Light Gray
val DarkSurface = Color(0xFF121316)        // Almost Black
val DarkOnSurface = Color(0xFFE2E2E6)      // Light Gray
val DarkSurfaceVariant = Color(0xFF44474F) // Medium Gray
val DarkOnSurfaceVariant = Color(0xFFC4C6D0) // Light Gray

val DarkOutline = Color(0xFF8E9099)        // Medium Gray
val DarkOutlineVariant = Color(0xFF44474F) // Dark Gray

// Special colors for post interactions and stats
val LikeColor = Color(0xFFFF4D4D)          // Bright Red for likes
val CommentColor = Color(0xFF4D9DFF)       // Bright Blue for comments
val BookmarkColor = Color(0xFFFFA64D)      // Bright Orange for bookmarks
val VerifiedBadge = Color(0xFF00CCBB)      // Teal for verified badge
val LinkColor = Color(0xFF2D7FF9)          // Blue for links

val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant
)

val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
)

object AppColors {
    val Like = LikeColor
    val Comment = CommentColor
    val Bookmark = BookmarkColor
    val Verified = VerifiedBadge
    val Link = LinkColor
}

@Composable
fun appColorScheme(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
    return if (darkTheme) DarkColorScheme else LightColorScheme
}