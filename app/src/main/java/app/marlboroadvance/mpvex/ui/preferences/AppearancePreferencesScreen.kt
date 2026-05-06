package app.marlboroadvance.mpvex.ui.preferences

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.R
import app.marlboroadvance.mpvex.preferences.AppearancePreferences
import app.marlboroadvance.mpvex.preferences.BrowserPreferences
import app.marlboroadvance.mpvex.preferences.GesturePreferences
import app.marlboroadvance.mpvex.preferences.MultiChoiceSegmentedButton
import app.marlboroadvance.mpvex.preferences.preference.collectAsState
import app.marlboroadvance.mpvex.presentation.Screen
import app.marlboroadvance.mpvex.ui.preferences.components.ThemePicker
import app.marlboroadvance.mpvex.ui.preferences.components.CustomColorToolbox
import app.marlboroadvance.mpvex.ui.theme.DarkMode
import app.marlboroadvance.mpvex.ui.utils.LocalBackStack
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import me.zhanghai.compose.preference.SliderPreference
import me.zhanghai.compose.preference.SwitchPreference
import org.koin.compose.koinInject
import kotlin.math.roundToInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Serializable
object AppearancePreferencesScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val preferences = koinInject<AppearancePreferences>()
        val browserPreferences = koinInject<BrowserPreferences>()
        val gesturePreferences = koinInject<GesturePreferences>()
        val backstack = LocalBackStack.current
        val systemDarkTheme = isSystemInDarkTheme()

        val darkMode by preferences.darkMode.collectAsState()
        val appTheme by preferences.appTheme.collectAsState()

        // Determine if we're in dark mode for theme preview
        val isDarkMode = when (darkMode) {
            DarkMode.Dark -> true
            DarkMode.Light -> false
            DarkMode.System -> systemDarkTheme
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.pref_appearance_title),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = backstack::removeLastOrNull) {
                            Icon(
                                Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    },
                )
            },
        ) { padding ->
            ProvidePreferenceLocals {
                LazyColumn(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    item {
                        PreferenceSectionHeader(title = stringResource(id = R.string.pref_appearance_category_theme))
                    }

                    item {
                        PreferenceCard {
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                // Dark mode selector
                                MultiChoiceSegmentedButton(
                                    choices = DarkMode.entries.map { stringResource(it.titleRes) }.toImmutableList(),
                                    selectedIndices = persistentListOf(DarkMode.entries.indexOf(darkMode)),
                                    onClick = { preferences.darkMode.set(DarkMode.entries[it]) },
                                )
                            }

                            PreferenceDivider()

                            // AMOLED mode state - need it before theme picker
                            val amoledMode by preferences.amoledMode.collectAsState()

                            // Theme picker - Aniyomi style
                            ThemePicker(
                                currentTheme = appTheme,
                                isDarkMode = isDarkMode,
                                onThemeSelected = { preferences.appTheme.set(it) },
                                modifier = Modifier.padding(vertical = 8.dp),
                            )

                            PreferenceDivider()

                            // AMOLED mode toggle
                            SwitchPreference(
                                value = amoledMode,
                                onValueChange = { newValue ->
                                    preferences.amoledMode.set(newValue)
                                },
                                title = { Text(text = stringResource(id = R.string.pref_appearance_amoled_mode_title)) },
                                summary = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_amoled_mode_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                                enabled = darkMode != DarkMode.Light
                            )
                            PreferenceDivider()

                            // Custom color toolbox
                            val useCustomColors by preferences.useCustomColors.collectAsState()
                            val customPrimary by preferences.customPrimaryColor.collectAsState()
                            val customSecondary by preferences.customSecondaryColor.collectAsState()
                            val customBackground by preferences.customBackgroundColor.collectAsState()
                            CustomColorToolbox(
                                primaryColor = androidx.compose.ui.graphics.Color(customPrimary),
                                secondaryColor = androidx.compose.ui.graphics.Color(customSecondary),
                                useCustomColors = useCustomColors,
                                onPrimaryChanged = { preferences.customPrimaryColor.set(it.toArgb()) },
                                onSecondaryChanged = { preferences.customSecondaryColor.set(it.toArgb()) },
                                onUseCustomChanged = { preferences.useCustomColors.set(it) },
                            )
                        }
                    }

                    item {
                        PreferenceSectionHeader(title = stringResource(R.string.pref_ui_customization))
                    }

                    item {
                        PreferenceCard {
                            val enable120fps by preferences.enable120fps.collectAsState()
                            SwitchPreference(
                                value = enable120fps,
                                onValueChange = { preferences.enable120fps.set(it) },
                                title = { Text(text = stringResource(R.string.pref_120fps_title)) },
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_120fps_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )

                            PreferenceDivider()

                            val enableMotionBlur by preferences.enableMotionBlur.collectAsState()
                            SwitchPreference(
                                value = enableMotionBlur,
                                onValueChange = { preferences.enableMotionBlur.set(it) },
                                title = { Text(text = stringResource(R.string.pref_motion_blur_title)) },
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_motion_blur_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )

                            PreferenceDivider()

                            val cornerRadius by preferences.uiCornerRadius.collectAsState()
                            SliderPreference(
                                value = cornerRadius.toFloat(),
                                onValueChange = { preferences.uiCornerRadius.set(it.roundToInt()) },
                                sliderValue = cornerRadius.toFloat(),
                                onSliderValueChange = { preferences.uiCornerRadius.set(it.roundToInt()) },
                                title = { Text(text = stringResource(R.string.pref_corner_radius_title)) },
                                valueRange = 0f..40f,
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_corner_radius_summary, cornerRadius),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )

                            PreferenceDivider()

                            val cardOpacity by preferences.cardOpacity.collectAsState()
                            SliderPreference(
                                value = cardOpacity,
                                onValueChange = { preferences.cardOpacity.set(it) },
                                sliderValue = cardOpacity,
                                onSliderValueChange = { preferences.cardOpacity.set(it) },
                                title = { Text(text = stringResource(R.string.pref_card_opacity_title)) },
                                valueRange = 0.1f..1f,
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_card_opacity_summary, (cardOpacity * 100).roundToInt()),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )

                            PreferenceDivider()

                            val navBarOpacity by preferences.navBarOpacity.collectAsState()
                            SliderPreference(
                                value = navBarOpacity,
                                onValueChange = { preferences.navBarOpacity.set(it) },
                                sliderValue = navBarOpacity,
                                onSliderValueChange = { preferences.navBarOpacity.set(it) },
                                title = { Text(text = stringResource(R.string.pref_nav_bar_opacity_title)) },
                                valueRange = 0.1f..1f,
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_nav_bar_opacity_summary, (navBarOpacity * 100).roundToInt()),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )
                        }
                    }

                    item {
                        PreferenceSectionHeader(title = stringResource(id = R.string.pref_appearance_category_file_browser))
                    }

                    item {
                        PreferenceCard {
                            val unlimitedNameLines by preferences.unlimitedNameLines.collectAsState()
                            SwitchPreference(
                                value = unlimitedNameLines,
                                onValueChange = { preferences.unlimitedNameLines.set(it) },
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_unlimited_name_lines_title),
                                    )
                                },
                                summary = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_unlimited_name_lines_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            )

                            PreferenceDivider()

                            val showUnplayedOldVideoLabel by preferences.showUnplayedOldVideoLabel.collectAsState()
                            SwitchPreference(
                                value = showUnplayedOldVideoLabel,
                                onValueChange = { preferences.showUnplayedOldVideoLabel.set(it) },
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_show_unplayed_old_video_label_title),
                                    )
                                },
                                summary = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_show_unplayed_old_video_label_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            )

                            PreferenceDivider()

                            val unplayedOldVideoDays by preferences.unplayedOldVideoDays.collectAsState()
                            SliderPreference(
                                value = unplayedOldVideoDays.toFloat(),
                                onValueChange = { preferences.unplayedOldVideoDays.set(it.roundToInt()) },
                                title = { Text(text = stringResource(id = R.string.pref_appearance_unplayed_old_video_days_title)) },
                                valueRange = 1f..30f,
                                summary = {
                                    Text(
                                        text = stringResource(
                                            id = R.string.pref_appearance_unplayed_old_video_days_summary,
                                            unplayedOldVideoDays,
                                        ),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                                onSliderValueChange = { preferences.unplayedOldVideoDays.set(it.roundToInt()) },
                                sliderValue = unplayedOldVideoDays.toFloat(),
                                enabled = showUnplayedOldVideoLabel
                            )

                            PreferenceDivider()

                            val autoScrollToLastPlayed by browserPreferences.autoScrollToLastPlayed.collectAsState()
                            SwitchPreference(
                                value = autoScrollToLastPlayed,
                                onValueChange = { browserPreferences.autoScrollToLastPlayed.set(it) },
                                title = {
                                    Text(text = stringResource(R.string.pref_appearance_auto_scroll_title))
                                },
                                summary = {
                                    Text(
                                        text = stringResource(R.string.pref_appearance_auto_scroll_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            )

                            PreferenceDivider()

                            val watchedThreshold by browserPreferences.watchedThreshold.collectAsState()
                            SliderPreference(
                                value = watchedThreshold.toFloat(),
                                onValueChange = { browserPreferences.watchedThreshold.set(it.roundToInt()) },
                                sliderValue = watchedThreshold.toFloat(),
                                onSliderValueChange = { browserPreferences.watchedThreshold.set(it.roundToInt()) },
                                title = { Text(text = stringResource(id = R.string.pref_appearance_watched_threshold_title)) },
                                valueRange = 50f..100f,
                                valueSteps = 9,
                                summary = {
                                    Text(
                                        text = stringResource(
                                            id = R.string.pref_appearance_watched_threshold_summary,
                                            watchedThreshold,
                                        ),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                },
                            )

                            PreferenceDivider()

                            val tapThumbnailToSelect by gesturePreferences.tapThumbnailToSelect.collectAsState()
                            SwitchPreference(
                                value = tapThumbnailToSelect,
                                onValueChange = { gesturePreferences.tapThumbnailToSelect.set(it) },
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.pref_gesture_tap_thumbnail_to_select_title),
                                    )
                                },
                                summary = {
                                    Text(
                                        text = stringResource(id = R.string.pref_gesture_tap_thumbnail_to_select_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            )

                            PreferenceDivider()

                            val showNetworkThumbnails by preferences.showNetworkThumbnails.collectAsState()
                            SwitchPreference(
                                value = showNetworkThumbnails,
                                onValueChange = { preferences.showNetworkThumbnails.set(it) },
                                title = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_show_network_thumbnails_title),
                                    )
                                },
                                summary = {
                                    Text(
                                        text = stringResource(id = R.string.pref_appearance_show_network_thumbnails_summary),
                                        color = MaterialTheme.colorScheme.outline,
                                    )
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}
