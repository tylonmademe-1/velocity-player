package app.marlboroadvance.mpvex.ui.player.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.preferences.PlayerButton
import app.marlboroadvance.mpvex.ui.player.Panels
import app.marlboroadvance.mpvex.ui.player.PlayerActivity
import app.marlboroadvance.mpvex.ui.player.PlayerViewModel
import app.marlboroadvance.mpvex.ui.player.Sheets
import app.marlboroadvance.mpvex.ui.player.VideoAspect
import app.marlboroadvance.mpvex.ui.player.controls.components.ControlsButton
import app.marlboroadvance.mpvex.ui.theme.controlColor
import app.marlboroadvance.mpvex.ui.theme.spacing
import dev.vivvvek.seeker.Segment

@Composable
fun TopLeftPlayerControlsLandscape(
  mediaTitle: String?,
  hideBackground: Boolean,
  onBackPress: () -> Unit,
  onOpenSheet: (Sheets) -> Unit,
  viewModel: PlayerViewModel,
) {
  val playlistModeEnabled = viewModel.hasPlaylistSupport()
  val clickEvent = LocalPlayerButtonsClickEvent.current

  Row(
    modifier = Modifier.width(IntrinsicSize.Max),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
  ) {
    ControlsButton(
      icon = Icons.AutoMirrored.Default.ArrowBack,
      onClick = onBackPress,
      color = if (hideBackground) controlColor else MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.size(45.dp),
    )

    val titleInteractionSource = remember { MutableInteractionSource() }

    Box(
      modifier =
        Modifier
          .height(45.dp)
          .clip(RoundedCornerShape(50))
          .background(
            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
              listOf(
                androidx.compose.ui.graphics.Color.White.copy(alpha = 0.18f),
                androidx.compose.ui.graphics.Color.White.copy(alpha = 0.08f),
              )
            )
          )
          .border(
            0.5.dp,
            androidx.compose.ui.graphics.Brush.verticalGradient(
              listOf(
                androidx.compose.ui.graphics.Color.White.copy(alpha = 0.35f),
                androidx.compose.ui.graphics.Color.White.copy(alpha = 0.08f),
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
          ),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
      ) {
        viewModel.getPlaylistInfo()?.let { playlistInfo ->
          Text(
            text = playlistInfo,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary,
          )
          Text(
            text = Typography.bullet.toString(),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f),
            overflow = TextOverflow.Clip,
          )
        }
        Text(
          text = mediaTitle ?: "",
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.bodyMedium,
          fontFamily = FontFamily.Monospace,
          color = androidx.compose.ui.graphics.Color.White,
          modifier = Modifier.weight(1f, fill = false),
        )
      }
    }
  }
}

@Composable
fun TopRightPlayerControlsLandscape(
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
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
  ) {
    buttons.forEach { button ->
      RenderPlayerButton(
        button = button,
        chapters = chapters,
        currentChapter = currentChapter,
        isPortrait = false,
        isSpeedNonOne = isSpeedNonOne,
        currentZoom = currentZoom,
        aspect = aspect,
        mediaTitle = mediaTitle,
        hideBackground = hideBackground,
        decoder = decoder,
        playbackSpeed = playbackSpeed,
        onBackPress = onBackPress,
        onOpenSheet = onOpenSheet,
        onOpenPanel = onOpenPanel,
        viewModel = viewModel,
        activity = activity,
        buttonSize = 45.dp,
      )
    }
  }
}

@Composable
fun BottomRightPlayerControlsLandscape(
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
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
  ) {
    buttons.forEach { button ->
      RenderPlayerButton(
        button = button,
        chapters = chapters,
        currentChapter = currentChapter,
        isPortrait = false,
        isSpeedNonOne = isSpeedNonOne,
        currentZoom = currentZoom,
        aspect = aspect,
        mediaTitle = mediaTitle,
        hideBackground = hideBackground,
        decoder = decoder,
        playbackSpeed = playbackSpeed,
        onBackPress = onBackPress,
        onOpenSheet = onOpenSheet,
        onOpenPanel = onOpenPanel,
        viewModel = viewModel,
        activity = activity,
        buttonSize = 45.dp,
      )
    }
  }
}

@Composable
fun BottomLeftPlayerControlsLandscape(
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
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
  ) {
    buttons.forEach { button ->
      RenderPlayerButton(
        button = button,
        chapters = chapters,
        currentChapter = currentChapter,
        isPortrait = false,
        isSpeedNonOne = isSpeedNonOne,
        currentZoom = currentZoom,
        aspect = aspect,
        mediaTitle = mediaTitle,
        hideBackground = hideBackground,
        decoder = decoder,
        playbackSpeed = playbackSpeed,
        onBackPress = onBackPress,
        onOpenSheet = onOpenSheet,
        onOpenPanel = onOpenPanel,
        viewModel = viewModel,
        activity = activity,
        buttonSize = 45.dp,
      )
    }
  }
}


