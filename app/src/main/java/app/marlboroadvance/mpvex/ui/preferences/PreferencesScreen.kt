package app.marlboroadvance.mpvex.ui.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ViewQuilt
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Gesture
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Subtitles
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.R
import app.marlboroadvance.mpvex.presentation.Screen
import app.marlboroadvance.mpvex.ui.theme.liquidGlass
import app.marlboroadvance.mpvex.ui.utils.LocalBackStack
import kotlinx.serialization.Serializable
import me.zhanghai.compose.preference.Preference
import me.zhanghai.compose.preference.ProvidePreferenceLocals

@Serializable
object PreferencesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = backstack::removeLastOrNull) {
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                )
            },
            containerColor = Color.Transparent,
        ) { padding ->
            ProvidePreferenceLocals {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    // Search bar
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .liquidGlass(
                                    shape = RoundedCornerShape(14.dp),
                                    tintColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                    borderColor = Color.White.copy(alpha = 0.2f),
                                )
                                .clickable { backstack.add(SettingsSearchScreen) },
                            shape = RoundedCornerShape(14.dp),
                            color = Color.Transparent,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 13.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline,
                                    modifier = Modifier.size(18.dp),
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = stringResource(R.string.settings_search_hint),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }
                        }
                    }

                    // UI & Appearance
                    item { PreferenceSectionHeader(title = "UI & Appearance") }
                    item {
                        PreferenceCard {
                            Preference(
                                title = { Text(stringResource(R.string.pref_appearance_title), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_appearance_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF5E5CE6)) {
                                        Icon(Icons.Outlined.Palette, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(AppearancePreferencesScreen) },
                            )
                            PreferenceDivider()
                            Preference(
                                title = { Text(stringResource(R.string.pref_layout_title), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_layout_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF30B0C7)) {
                                        Icon(Icons.AutoMirrored.Outlined.ViewQuilt, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(PlayerControlsPreferencesScreen) },
                            )
                        }
                    }

                    // Playback & Controls
                    item { PreferenceSectionHeader(title = "Playback & Controls") }
                    item {
                        PreferenceCard {
                            Preference(
                                title = { Text(stringResource(R.string.pref_player), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_player_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFFFF6B35)) {
                                        Icon(Icons.Outlined.PlayCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(PlayerPreferencesScreen) },
                            )
                            PreferenceDivider()
                            Preference(
                                title = { Text(stringResource(R.string.pref_gesture), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_gesture_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF34C759)) {
                                        Icon(Icons.Outlined.Gesture, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(GesturePreferencesScreen) },
                            )
                        }
                    }

                    // File Management
                    item { PreferenceSectionHeader(title = "File Management") }
                    item {
                        PreferenceCard {
                            Preference(
                                title = { Text(stringResource(R.string.pref_folders_title), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_folders_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFFFF9F0A)) {
                                        Icon(Icons.Outlined.Folder, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(FoldersPreferencesScreen) },
                            )
                        }
                    }

                    // Media Settings
                    item { PreferenceSectionHeader(title = "Media Settings") }
                    item {
                        PreferenceCard {
                            Preference(
                                title = { Text(stringResource(R.string.pref_decoder), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_decoder_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFFAC8E68)) {
                                        Icon(Icons.Outlined.Memory, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(DecoderPreferencesScreen) },
                            )
                            PreferenceDivider()
                            Preference(
                                title = { Text(stringResource(R.string.pref_subtitles), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_subtitles_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF5E5CE6)) {
                                        Icon(Icons.Outlined.Subtitles, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(SubtitlesPreferencesScreen) },
                            )
                            PreferenceDivider()
                            Preference(
                                title = { Text(stringResource(R.string.pref_audio), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_audio_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFFFF375F)) {
                                        Icon(Icons.Outlined.Audiotrack, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(AudioPreferencesScreen) },
                            )
                        }
                    }

                    // Advanced & About
                    item { PreferenceSectionHeader(title = "Advanced & About") }
                    item {
                        PreferenceCard {
                            Preference(
                                title = { Text(stringResource(R.string.pref_advanced), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_advanced_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF636366)) {
                                        Icon(Icons.Outlined.Code, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(AdvancedPreferencesScreen) },
                            )
                            PreferenceDivider()
                            Preference(
                                title = { Text(stringResource(R.string.pref_about_title), fontWeight = FontWeight.Medium) },
                                summary = { Text(stringResource(R.string.pref_about_summary), color = MaterialTheme.colorScheme.outline, style = MaterialTheme.typography.bodySmall) },
                                icon = {
                                    IosIconContainer(color = Color(0xFF30B0C7)) {
                                        Icon(Icons.Outlined.Info, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                },
                                onClick = { backstack.add(AboutScreen) },
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.padding(bottom = 24.dp)) }
                }
            }
        }
    }
}
