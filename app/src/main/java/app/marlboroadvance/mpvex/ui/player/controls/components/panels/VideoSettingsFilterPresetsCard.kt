package app.marlboroadvance.mpvex.ui.player.controls.components.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.preferences.DecoderPreferences
import app.marlboroadvance.mpvex.preferences.preference.collectAsState
import app.marlboroadvance.mpvex.presentation.components.ExpandableCard
import app.marlboroadvance.mpvex.ui.player.FilterPreset
import app.marlboroadvance.mpvex.ui.player.controls.CARDS_MAX_WIDTH
import app.marlboroadvance.mpvex.ui.player.controls.panelCardsColors
import app.marlboroadvance.mpvex.ui.theme.spacing
import `is`.xyz.mpv.MPVLib
import org.koin.compose.koinInject

// Color accent per preset category
private fun presetColor(preset: FilterPreset): Color = when (preset) {
    FilterPreset.NONE -> Color(0xFF8E8E93)
    FilterPreset.VIVID -> Color(0xFFFF6B35)
    FilterPreset.WARM_TONE -> Color(0xFFFF9F0A)
    FilterPreset.COOL_TONE -> Color(0xFF30B0C7)
    FilterPreset.SOFT_PASTEL -> Color(0xFFFF375F)
    FilterPreset.CINEMATIC -> Color(0xFF5E5CE6)
    FilterPreset.DRAMATIC -> Color(0xFFAC8E68)
    FilterPreset.NIGHT_MODE -> Color(0xFF1C1C1E)
    FilterPreset.NOSTALGIC -> Color(0xFFD4A017)
    FilterPreset.GHIBLI_STYLE -> Color(0xFF34C759)
    FilterPreset.NEON_POP -> Color(0xFFBF5AF2)
    FilterPreset.DEEP_BLACK -> Color(0xFF2C2C2E)
    FilterPreset.GOLDEN_HOUR -> Color(0xFFFF8C00)
    FilterPreset.ARCTIC -> Color(0xFF64D2FF)
    FilterPreset.BLEACH_BYPASS -> Color(0xFF636366)
    FilterPreset.TEAL_ORANGE -> Color(0xFF00C7BE)
    FilterPreset.ANIME -> Color(0xFFFF2D55)
    FilterPreset.HORROR -> Color(0xFF8B0000)
    FilterPreset.RETRO_VHS -> Color(0xFFD4A017)
    FilterPreset.ULTRA_SHARP -> Color(0xFF0A84FF)
    FilterPreset.DOCUMENTARY -> Color(0xFF6D6D72)
    FilterPreset.SUNSET -> Color(0xFFFF6B6B)
    FilterPreset.MOONLIGHT -> Color(0xFF5E5CE6)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VideoSettingsFilterPresetsCard(modifier: Modifier = Modifier) {
    val decoderPreferences = koinInject<DecoderPreferences>()
    var isExpanded by remember { mutableStateOf(true) }

    val brightness by decoderPreferences.brightnessFilter.collectAsState()
    val saturation by decoderPreferences.saturationFilter.collectAsState()
    val contrast by decoderPreferences.contrastFilter.collectAsState()
    val gamma by decoderPreferences.gammaFilter.collectAsState()
    val hue by decoderPreferences.hueFilter.collectAsState()
    val sharpness by decoderPreferences.sharpnessFilter.collectAsState()

    val currentPreset = FilterPreset.entries.find { preset ->
        preset.brightness == brightness &&
            preset.saturation == saturation &&
            preset.contrast == contrast &&
            preset.gamma == gamma &&
            preset.hue == hue &&
            preset.sharpness == sharpness
    }

    ExpandableCard(
        isExpanded = isExpanded,
        onExpand = { isExpanded = !isExpanded },
        title = {
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
                Icon(Icons.Default.AutoAwesome, null, tint = MaterialTheme.colorScheme.primary)
                Text("Filter Presets", fontWeight = FontWeight.SemiBold)
            }
        },
        colors = panelCardsColors(),
        modifier = modifier.widthIn(max = CARDS_MAX_WIDTH),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterPreset.entries.forEach { preset ->
                    val isSelected = currentPreset == preset
                    val accentColor = presetColor(preset)

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                brush = if (isSelected) {
                                    Brush.linearGradient(
                                        colors = listOf(
                                            accentColor.copy(alpha = 0.85f),
                                            accentColor.copy(alpha = 0.55f),
                                        )
                                    )
                                } else {
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.55f),
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.35f),
                                        )
                                    )
                                }
                            )
                            .border(
                                width = 0.5.dp,
                                brush = Brush.verticalGradient(
                                    colors = if (isSelected) listOf(
                                        Color.White.copy(alpha = 0.40f),
                                        Color.White.copy(alpha = 0.10f),
                                    ) else listOf(
                                        Color.White.copy(alpha = 0.20f),
                                        Color.White.copy(alpha = 0.05f),
                                    )
                                ),
                                shape = RoundedCornerShape(20.dp),
                            )
                            .clickable {
                                decoderPreferences.brightnessFilter.set(preset.brightness)
                                decoderPreferences.saturationFilter.set(preset.saturation)
                                decoderPreferences.contrastFilter.set(preset.contrast)
                                decoderPreferences.gammaFilter.set(preset.gamma)
                                decoderPreferences.hueFilter.set(preset.hue)
                                decoderPreferences.sharpnessFilter.set(preset.sharpness)
                                MPVLib.setPropertyInt("brightness", preset.brightness)
                                MPVLib.setPropertyInt("saturation", preset.saturation)
                                MPVLib.setPropertyInt("contrast", preset.contrast)
                                MPVLib.setPropertyInt("gamma", preset.gamma)
                                MPVLib.setPropertyInt("hue", preset.hue)
                                MPVLib.setPropertyInt("sharpen", preset.sharpness)
                            }
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            // Color dot
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color.White else accentColor)
                            )
                            Text(
                                text = preset.displayName,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            )
                            if (isSelected) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp),
                                )
                            }
                        }
                    }
                }
            }

            // Description of selected preset
            currentPreset?.let { preset ->
                if (preset.description.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        presetColor(preset).copy(alpha = 0.15f),
                                        presetColor(preset).copy(alpha = 0.05f),
                                    )
                                )
                            )
                            .border(
                                0.5.dp,
                                presetColor(preset).copy(alpha = 0.30f),
                                RoundedCornerShape(12.dp),
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = preset.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        )
                    }
                }
            }
        }
    }
}
