package app.marlboroadvance.mpvex.ui.player.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.preferences.PlayerButton
import app.marlboroadvance.mpvex.ui.player.Panels
import app.marlboroadvance.mpvex.ui.player.PlayerActivity
import app.marlboroadvance.mpvex.ui.player.PlayerViewModel
import app.marlboroadvance.mpvex.ui.player.Sheets
import app.marlboroadvance.mpvex.ui.player.VideoAspect
import app.marlboroadvance.mpvex.ui.player.controls.components.ControlsButton
import app.marlboroadvance.mpvex.ui.player.controls.components.ControlsGroup
import app.marlboroadvance.mpvex.ui.theme.controlColor
import app.marlboroadvance.mpvex.ui.theme.spacing
import dev.vivvvek.seeker.Segment

@Composable
fun TopPlayerControlsPortrait(
    mediaTitle: String?,
    hideBackground: Boolean,
    onBackPress: () -> Unit,
    onOpenSheet: (Sheets) -> Unit,
    viewModel: PlayerViewModel,
) {
    val playlistModeEnabled = viewModel.hasPlaylistSupport()
    val clickEvent = LocalPlayerButtonsClickEvent.current

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ControlsGroup {
                ControlsButton(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = onBackPress,
                    color = Color.White,
                )

                // iOS 26 frosted glass title pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.18f),
                                    Color.White.copy(alpha = 0.08f),
                                )
                            )
                        )
                        .border(
                            0.5.dp,
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.35f),
                                    Color.White.copy(alpha = 0.08f),
                                )
                            ),
                            RoundedCornerShape(50),
                        )
                        .clickable(
                            enabled = playlistModeEnabled,
                            onClick = {
                                clickEvent()
                                onOpenSheet(Sheets.Playlist)
                            },
                        )
                        .padding(horizontal = 14.dp, vertical = 7.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
                    ) {
                        viewModel.getPlaylistInfo()?.let { playlistInfo ->
                            Text(
                                text = playlistInfo,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = Typography.bullet.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                color = Color.White.copy(alpha = 0.6f),
                            )
                        }
                        Text(
                            text = mediaTitle ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White,
                            modifier = Modifier.weight(1f, fill = false),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomPlayerControlsPortrait(
    buttons: List<PlayerButton>,
    chapters: List<Segment>,
    currentChapter: Int?,
    isSpeedNonOne: Boolean,
    currentZoom: Float,
    aspect: VideoAspect,
    mediaTitle: String?,
    hideBackground: Boolean,
    decoder: app.marlboroadvance.mpvex.ui.player.Decoder,
    playbackSpeed: Float,
    onBackPress: () -> Unit,
    onOpenSheet: (Sheets) -> Unit,
    onOpenPanel: (Panels) -> Unit,
    viewModel: PlayerViewModel,
    activity: PlayerActivity,
) {
    // iOS 26 frosted glass bottom controls bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.14f),
                        Color.White.copy(alpha = 0.06f),
                    )
                )
            )
            .border(
                0.5.dp,
                Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.32f),
                        Color.White.copy(alpha = 0.07f),
                    )
                ),
                RoundedCornerShape(32.dp),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ControlsGroup {
                buttons.forEach { button ->
                    RenderPlayerButton(
                        button = button,
                        chapters = chapters,
                        currentChapter = currentChapter,
                        isPortrait = true,
                        isSpeedNonOne = isSpeedNonOne,
                        currentZoom = currentZoom,
                        aspect = aspect,
                        mediaTitle = mediaTitle,
                        hideBackground = hideBackground,
                        onBackPress = onBackPress,
                        onOpenSheet = onOpenSheet,
                        onOpenPanel = onOpenPanel,
                        viewModel = viewModel,
                        activity = activity,
                        decoder = decoder,
                        playbackSpeed = playbackSpeed,
                        buttonSize = 48.dp,
                    )
                }
            }
        }
    }
}
