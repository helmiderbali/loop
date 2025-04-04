package com.hderbali.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.yourapp.core.ui.theme.AppTypography


data class AppThemeExtras(
    val isLightTheme: Boolean,
    val likeColor: Color,
    val commentColor: Color,
    val bookmarkColor: Color,
    val verifiedColor: Color,
    val linkColor: Color
)

val LocalAppThemeExtras = staticCompositionLocalOf {
    AppThemeExtras(
        isLightTheme = true,
        likeColor = Color.Unspecified,
        commentColor = Color.Unspecified,
        bookmarkColor = Color.Unspecified,
        verifiedColor = Color.Unspecified,
        linkColor = Color.Unspecified
    )
}

class ExtendedThemeAccessor(private val extras: AppThemeExtras) {
    val likeColor: Color get() = extras.likeColor
    val commentColor: Color get() = extras.commentColor
    val bookmarkColor: Color get() = extras.bookmarkColor
    val verifiedColor: Color get() = extras.verifiedColor
    val linkColor: Color get() = extras.linkColor
    val isLightTheme: Boolean get() = extras.isLightTheme
}

val ExtendedTheme: ExtendedThemeAccessor
    @Composable
    get() = ExtendedThemeAccessor(LocalAppThemeExtras.current)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appThemeExtras = AppThemeExtras(
        isLightTheme = !darkTheme,
        likeColor = AppColors.Like,
        commentColor = AppColors.Comment,
        bookmarkColor = AppColors.Bookmark,
        verifiedColor = AppColors.Verified,
        linkColor = AppColors.Link
    )

    val colorScheme = appColorScheme(darkTheme)

    CompositionLocalProvider(
        LocalAppThemeExtras provides appThemeExtras
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            shapes = AppShapes
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                content()
            }
        }
    }
}
