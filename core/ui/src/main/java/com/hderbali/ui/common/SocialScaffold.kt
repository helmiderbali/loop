import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.hderbali.common.NavRoutes
import com.hderbali.ui.common.SocialBottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialScaffold(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    onCameraClick: () -> Unit,
    topBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val showBottomBar = when (currentRoute) {
        NavRoutes.SPLASH, NavRoutes.CAMERA -> false
        else -> true
    }

    Scaffold(
        topBar = topBar,
        snackbarHost = snackbarHost,
        floatingActionButton = {
            if (currentRoute != NavRoutes.SPLASH && currentRoute != NavRoutes.CAMERA) {
                FloatingActionButton(
                    onClick = onCameraClick,
                    modifier = Modifier.offset(y = 48.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Prendre une photo",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            AnimatedVisibility(visible = showBottomBar) {
                SocialBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = onNavigate
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = if (showBottomBar) paddingValues.calculateBottomPadding() else 0.dp
                )
        ) {
            content(paddingValues)
        }
    }
}
