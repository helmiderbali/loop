package com.hderbali.splash.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hderbali.splash.R
import com.hderbali.splash.SplashScreenState
import com.hderbali.ui.theme.AppDimensions

@Composable
fun SplashScreenContent(
    state: SplashScreenState,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(AppDimensions.spacing16)
        ) {
            LogoAnimation(isLoading = state.isLoading)
            Spacer(modifier = Modifier.height(AppDimensions.spacing32))

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
                state.error != null -> {
                    ErrorState(
                        errorMessage = state.error,
                        onRetryClick = onRetryClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoAnimation(isLoading: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_animation")
    val scale = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_animation"
    ).value

    val animationModifier = if (isLoading) Modifier.scale(scale) else Modifier

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(1000)),
        exit = fadeOut(animationSpec = tween(1000))
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo de l'application",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = animationModifier.size(150.dp)
        )
    }
}
@Composable
private fun ErrorState(
    errorMessage: String?,
    onRetryClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage ?: "Une erreur s'est produite",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(AppDimensions.spacing16))

        Button(onClick = onRetryClick) {
            Text("Réessayer")
        }
    }
}
