package com.artemissoftware.firegallery.screens.splash.composables

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.theme.Orange
import com.artemissoftware.common.theme.RedOrange
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.SplashScreen
import kotlinx.coroutines.launch

@Composable
fun Logo(
    modifier: Modifier = Modifier,
    preRevealColor: Color = Orange,
    borderColor: Color = RedOrange,
    logoColor: Color = Red
) {

    val boxSize = 180.dp
    val borderWidth = 2.dp


    val animateShape = remember { Animatable(boxSize.value) }
    val animateColor = remember { Animatable(preRevealColor) }

    LaunchedEffect(true) {

        val jobA = launch {
            animateShape.animateTo(
                targetValue = borderWidth.value,
                animationSpec = repeatable(
                    animation = tween(
                        durationMillis = 1500,
                        easing = LinearEasing,
                        delayMillis = 10
                    ),
                    repeatMode = RepeatMode.Restart,
                    iterations = 1
                )
            )
        }

        val jobB = launch {
            animateColor.animateTo(
                targetValue = borderColor,
                animationSpec = repeatable(
                    animation = tween(
                        durationMillis = 2000,
                        easing = LinearEasing,
                        delayMillis = 100
                    ),
                    repeatMode = RepeatMode.Restart,
                    iterations = 1
                )
            )
        }

        jobA.join()
        jobB.join()

    }




    Box(
        modifier = modifier
            .size(boxSize)
            .clip(CircleShape)
            .background(color = preRevealColor)
            .border(
                width = Dp(animateShape.value),
                color = animateColor.value,
                shape = CircleShape
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_flame),
            contentDescription = "Compose image",
            colorFilter =  ColorFilter.tint(color = logoColor),
            modifier = Modifier
                .size(140.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoPreview() {

    Logo()
}