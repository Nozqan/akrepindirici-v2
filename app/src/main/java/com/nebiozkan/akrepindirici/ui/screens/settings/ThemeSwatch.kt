package com.nebiozkan.akrepindirici.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nebiozkan.akrepindirici.R
import com.nebiozkan.akrepindirici.ui.theme.AppColors
import com.nebiozkan.akrepindirici.ui.theme.AppThemeOption
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors

fun labelFor(option: AppThemeOption): Int = when (option) {
    AppThemeOption.BLACK_PEARL -> R.string.theme_black_pearl
    AppThemeOption.MIDNIGHT_BLUE -> R.string.theme_midnight_blue
    AppThemeOption.EMERALD -> R.string.theme_emerald
    AppThemeOption.RUBY -> R.string.theme_ruby
    AppThemeOption.FOUNDER_SPECIAL -> R.string.theme_special
}

@Composable
fun ThemeSwatch(
    palette: AppColors,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeColors = LocalAppColors.current
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(palette.accent)
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) activeColors.textPrimary else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = palette.onAccent
                )
            }
        }
        Text(
            text = stringResource(labelFor(palette.option)),
            style = MaterialTheme.typography.labelMedium,
            color = activeColors.textSecondary,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
