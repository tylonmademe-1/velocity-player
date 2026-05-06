package app.marlboroadvance.mpvex.ui.preferences.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import app.marlboroadvance.mpvex.R
import app.marlboroadvance.mpvex.ui.theme.liquidGlass

private val colorPresets = listOf(
    // Pinks & Reds
    Color(0xFFFF1493), Color(0xFFDC143C), Color(0xFFFF69B4),
    Color(0xFFC2185B), Color(0xFF880E4F), Color(0xFFE91E8C),
    // Yellows & Golds
    Color(0xFFFFD700), Color(0xFFFFB300), Color(0xFFF9A825),
    Color(0xFFB8860B), Color(0xFFFF8F00), Color(0xFFFFCC02),
    // Purples
    Color(0xFF7B1FA2), Color(0xFF6A1B9A), Color(0xFFAA00FF),
    Color(0xFF4527A0), Color(0xFF9C27B0), Color(0xFFCE93D8),
    // Blues
    Color(0xFF1565C0), Color(0xFF0288D1), Color(0xFF1E88E5),
    Color(0xFF0D47A1), Color(0xFF5E81AC), Color(0xFF64B5F6),
    // Greens
    Color(0xFF2E7D32), Color(0xFF00897B), Color(0xFF00A86B),
    Color(0xFF43A047), Color(0xFF1B5E20), Color(0xFF69FF47),
    // Oranges & Browns
    Color(0xFFE65100), Color(0xFFBF360C), Color(0xFFFF7043),
    Color(0xFFB87333), Color(0xFFFF5722), Color(0xFFFF9800),
    // Neutrals
    Color(0xFF212121), Color(0xFF424242), Color(0xFF757575),
    Color(0xFF9E9E9E), Color(0xFFBDBDBD), Color(0xFFFFFFFF),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerDialog(
    title: String,
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit,
) {
    var hexInput by remember { mutableStateOf(currentColor.toHex()) }
    var previewColor by remember { mutableStateOf(currentColor) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = {
            Column {
                // Preview swatch
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(previewColor)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            RoundedCornerShape(16.dp),
                        )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Hex input
                OutlinedTextField(
                    value = hexInput,
                    onValueChange = { input ->
                        hexInput = input.uppercase().take(7)
                        parseHex(input)?.let { previewColor = it }
                    },
                    label = { Text("Hex Color (#RRGGBB)") },
                    placeholder = { Text("#FF1493") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            parseHex(hexInput)?.let {
                                previewColor = it
                                onColorSelected(it)
                                onDismiss()
                            }
                        }
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.pref_custom_color_hint),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Color swatches grid
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    colorPresets.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (color == previewColor) 3.dp else 1.dp,
                                    color = if (color == previewColor)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    shape = CircleShape,
                                )
                                .clickable {
                                    previewColor = color
                                    hexInput = color.toHex()
                                },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onColorSelected(previewColor)
                onDismiss()
            }) {
                Text(stringResource(R.string.generic_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.generic_cancel))
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomColorToolbox(
    primaryColor: Color,
    secondaryColor: Color,
    useCustomColors: Boolean,
    onPrimaryChanged: (Color) -> Unit,
    onSecondaryChanged: (Color) -> Unit,
    onUseCustomChanged: (Boolean) -> Unit,
) {
    var showPrimaryPicker by remember { mutableStateOf(false) }
    var showSecondaryPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.pref_custom_colors),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            androidx.compose.material3.Switch(
                checked = useCustomColors,
                onCheckedChange = onUseCustomChanged,
            )
        }

        if (useCustomColors) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.pref_custom_color_hint),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Primary color row
            ColorSwatchRow(
                label = stringResource(R.string.pref_custom_primary_color),
                color = primaryColor,
                onClick = { showPrimaryPicker = true },
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Secondary color row
            ColorSwatchRow(
                label = stringResource(R.string.pref_custom_secondary_color),
                color = secondaryColor,
                onClick = { showSecondaryPicker = true },
            )
        }
    }

    if (showPrimaryPicker) {
        ColorPickerDialog(
            title = stringResource(R.string.pref_custom_primary_color),
            currentColor = primaryColor,
            onColorSelected = onPrimaryChanged,
            onDismiss = { showPrimaryPicker = false },
        )
    }
    if (showSecondaryPicker) {
        ColorPickerDialog(
            title = stringResource(R.string.pref_custom_secondary_color),
            currentColor = secondaryColor,
            onColorSelected = onSecondaryChanged,
            onDismiss = { showSecondaryPicker = false },
        )
    }
}

@Composable
private fun ColorSwatchRow(
    label: String,
    color: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = color.toHex(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), CircleShape),
            )
        }
    }
}

private fun Color.toHex(): String {
    val argb = toArgb()
    return "#%06X".format(argb and 0xFFFFFF)
}

private fun parseHex(hex: String): Color? {
    val clean = hex.trimStart('#')
    if (clean.length != 6) return null
    return try {
        val r = clean.substring(0, 2).toInt(16)
        val g = clean.substring(2, 4).toInt(16)
        val b = clean.substring(4, 6).toInt(16)
        Color(r, g, b)
    } catch (e: NumberFormatException) {
        null
    }
}
