package app.marlboroadvance.mpvex.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.liquidGlass(
    shape: Shape? = null,
    blurRadius: Dp = 20.dp,
    tintColor: Color? = null,
    borderColor: Color? = null,
): Modifier = composed {
    val customization = LocalUiCustomization.current
    val resolvedShape = shape ?: RoundedCornerShape(customization.cornerRadius.dp)
    val resolvedTint = tintColor ?: Color.White.copy(alpha = customization.cardOpacity * 0.18f)
    val topHighlight = borderColor ?: Color.White.copy(alpha = 0.28f)

    this
        .clip(resolvedShape)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    resolvedTint.copy(alpha = (resolvedTint.alpha * 1.3f).coerceAtMost(1f)),
                    resolvedTint,
                )
            ),
            shape = resolvedShape,
        )
        .border(
            width = 0.5.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    topHighlight,
                    topHighlight.copy(alpha = topHighlight.alpha * 0.2f),
                )
            ),
            shape = resolvedShape,
        )
}

fun Modifier.liquidGlassDark(
    shape: Shape? = null,
    blurRadius: Dp = 24.dp,
): Modifier = liquidGlass(
    shape = shape,
    blurRadius = blurRadius,
    tintColor = Color.Black.copy(alpha = 0.40f),
    borderColor = Color.White.copy(alpha = 0.12f),
)

fun Modifier.motionBlur(velocityX: Float = 0f, velocityY: Float = 0f): Modifier = composed {
    val customization = LocalUiCustomization.current
    if (!customization.motionBlurEnabled) this
    else this.graphicsLayer { alpha = 0.99f }
}
