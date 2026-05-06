package app.marlboroadvance.mpvex.ui.player.controls.components.panels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.BlurOn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.R
import app.marlboroadvance.mpvex.preferences.DecoderPreferences
import app.marlboroadvance.mpvex.preferences.preference.collectAsState
import app.marlboroadvance.mpvex.preferences.preference.deleteAndGet
import app.marlboroadvance.mpvex.presentation.components.ExpandableCard
import app.marlboroadvance.mpvex.presentation.components.SliderItem
import app.marlboroadvance.mpvex.ui.player.VideoFilters
import app.marlboroadvance.mpvex.ui.player.controls.CARDS_MAX_WIDTH
import app.marlboroadvance.mpvex.ui.player.controls.panelCardsColors
import app.marlboroadvance.mpvex.ui.theme.spacing
import `is`.xyz.mpv.MPVLib
import me.zhanghai.compose.preference.FooterPreference
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import org.koin.compose.koinInject

private fun filterIcon(filter: VideoFilters): ImageVector = when (filter) {
    VideoFilters.BRIGHTNESS -> Icons.Default.Brightness6
    VideoFilters.SATURATION -> Icons.Default.ColorLens
    VideoFilters.CONTRAST -> Icons.Default.Contrast
    VideoFilters.GAMMA -> Icons.Default.Flare
    VideoFilters.HUE -> Icons.Default.InvertColors
    VideoFilters.SHARPNESS -> Icons.Default.BlurOn
}

private fun filterColor(filter: VideoFilters): Color = when (filter) {
    VideoFilters.BRIGHTNESS -> Color(0xFFFF9F0A)
    VideoFilters.SATURATION -> Color(0xFFFF375F)
    VideoFilters.CONTRAST -> Color(0xFF5E5CE6)
    VideoFilters.GAMMA -> Color(0xFF30B0C7)
    VideoFilters.HUE -> Color(0xFFBF5AF2)
    VideoFilters.SHARPNESS -> Color(0xFF34C759)
}

@Composable
fun VideoSettingsFiltersCard(modifier: Modifier = Modifier) {
    val decoderPreferences = koinInject<DecoderPreferences>()
    var isExpanded by remember { mutableStateOf(true) }

    ExpandableCard(
        isExpanded = isExpanded,
        onExpand = { isExpanded = !isExpanded },
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Default.Tune, null, tint = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.player_sheets_filters_title), fontWeight = FontWeight.SemiBold)
            }
        },
        colors = panelCardsColors(),
        modifier = modifier.widthIn(max = CARDS_MAX_WIDTH),
    ) {
        ProvidePreferenceLocals {
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                // Reset button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            VideoFilters.entries.forEach {
                                MPVLib.setPropertyInt(it.mpvProperty, it.preference(decoderPreferences).deleteAndGet())
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.generic_reset),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                VideoFilters.entries.forEach { filter ->
                    val value by filter.preference(decoderPreferences).collectAsState()
                    val accentColor = filterColor(filter)
                    val icon = filterIcon(filter)

                    // iOS-style filter row with colored icon badge
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.45f),
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
                                    )
                                )
                            )
                            .border(
                                0.5.dp,
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.20f),
                                        Color.White.copy(alpha = 0.04f),
                                    )
                                ),
                                RoundedCornerShape(16.dp),
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(top = 6.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            brush = Brush.linearGradient(
                                                colors = listOf(
                                                    accentColor.copy(alpha = 0.9f),
                                                    accentColor.copy(alpha = 0.6f),
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp),
                                    )
                                }
                                Text(
                                    text = stringResource(filter.titleRes),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                )
                                Text(
                                    text = value.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (value != 0) accentColor else MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.weight(1f),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.End,
                                )
                            }
                            SliderItem(
                                label = "",
                                value = value,
                                valueText = value.toString(),
                                onChange = {
                                    filter.preference(decoderPreferences).set(it)
                                    MPVLib.setPropertyInt(filter.mpvProperty, it)
                                },
                                max = filter.max,
                                min = filter.min,
                            )
                        }
                    }
                }

                if (!decoderPreferences.gpuNext.get()) {
                    FooterPreference(
                        summary = {
                            Text(text = stringResource(id = R.string.player_sheets_filters_warning))
                        },
                    )
                }
            }
        }
    }
}
