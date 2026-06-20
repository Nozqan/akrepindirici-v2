package com.nebiozkan.akrepindirici.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.nebiozkan.akrepindirici.BuildConfig
import com.nebiozkan.akrepindirici.R
import com.nebiozkan.akrepindirici.ui.theme.AppPalettes
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors
import java.util.Calendar

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val settings by viewModel.settings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundGradient)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.headlineMedium,
            color = colors.textPrimary,
            modifier = Modifier.padding(top = 24.dp, bottom = 20.dp)
        )

        // Founder / mission card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(colors.surface)
                .padding(18.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_founder_label),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Text(
                text = stringResource(R.string.settings_founder_name),
                style = MaterialTheme.typography.titleLarge,
                color = colors.textPrimary,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )
            Text(
                text = stringResource(R.string.settings_mission_label),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Text(
                text = stringResource(R.string.settings_mission_text),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        SectionLabel(stringResource(R.string.settings_security_section))

        SettingsToggleRow(
            title = stringResource(R.string.settings_biometric_lock),
            subtitle = stringResource(R.string.settings_biometric_lock_desc),
            checked = settings.biometricLockEnabled,
            onCheckedChange = viewModel::onBiometricLockToggled
        )

        SectionLabel(stringResource(R.string.settings_personalization_section))

        SettingsToggleRow(
            title = "Siyah / Beyaz Tema",
            subtitle = "Tüm renkleri devre dışı bırakıp monokrom moda geç",
            checked = settings.blackWhiteMode,
            onCheckedChange = viewModel::onBlackWhiteModeToggled
        )

        Text(
            text = stringResource(R.string.settings_themes_label),
            style = MaterialTheme.typography.titleMedium,
            color = colors.textPrimary,
            modifier = Modifier.padding(top = 18.dp, bottom = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(AppPalettes.all) { palette ->
                ThemeSwatch(
                    palette = palette,
                    isSelected = palette.option == settings.themeOption,
                    onClick = { viewModel.onThemeSelected(palette.option) }
                )
            }
        }

        Spacer(Modifier.padding(top = 30.dp))

        Text(
            text = stringResource(
                R.string.settings_version,
                BuildConfig.VERSION_NAME,
                Calendar.getInstance().get(Calendar.YEAR)
            ),
            style = MaterialTheme.typography.labelMedium,
            color = colors.textSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    val colors = LocalAppColors.current
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = colors.textSecondary,
        modifier = Modifier.padding(top = 22.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val colors = LocalAppColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = colors.textPrimary)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = colors.onAccent,
                checkedTrackColor = colors.accent,
                uncheckedTrackColor = colors.divider
            )
        )
    }
    HorizontalDivider(color = colors.divider, modifier = Modifier.padding(top = 0.dp))
}
