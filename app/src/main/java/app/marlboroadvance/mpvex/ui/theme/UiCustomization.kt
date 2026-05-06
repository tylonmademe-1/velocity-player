package app.marlboroadvance.mpvex.ui.theme

import androidx.compose.runtime.compositionLocalOf

data class UiCustomization(
    val cornerRadius: Int = 28,
    val cardOpacity: Float = 0.6f,
    val navBarOpacity: Float = 0.72f,
    val motionBlurEnabled: Boolean = true,
)

val LocalUiCustomization = compositionLocalOf { UiCustomization() }
