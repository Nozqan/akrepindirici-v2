package com.nebiozkan.akrepindirici.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Root theme wrapper. Wraps Material3 theming AND provides [LocalAppColors]
 * so that the five custom palettes (Black Pearl, Midnight Blue, Emerald,
 * Ruby, Founder Special) drive both Material components and our own
 * hand-rolled UI consistently.
 *
 * @param themeOption the user's selected palette, read from SettingsViewModel
 * @param useBlackWhiteMode global black/white toggle described in the spec;
 *   when true, the app falls back to a strict monochrome scheme regardless
 *   of the selected color palette.
 */
@Composable
fun AkrepIndiriciTheme(
    themeOption: AppThemeOption,
    useBlackWhiteMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val appColors = if (useBlackWhiteMode) {
        monochromeColors(isSystemInDarkTheme())
    } else {
        AppPalettes.paletteFor(themeOption)
    }

    val materialScheme = if (useBlackWhiteMode && !isSystemInDarkTheme()) {
        lightColorScheme(
            primary = appColors.accent,
            background = appColors.background,
            surface = appColors.surface,
            onPrimary = appColors.onAccent,
            onBackground = appColors.textPrimary,
            onSurface = appColors.textPrimary,
            error = appColors.error
        )
    } else {
        darkColorScheme(
            primary = appColors.accent,
            secondary = appColors.accentVariant,
            background = appColors.background,
            surface = appColors.surface,
            surfaceVariant = appColors.surfaceVariant,
            onPrimary = appColors.onAccent,
            onBackground = appColors.textPrimary,
            onSurface = appColors.textPrimary,
            onSurfaceVariant = appColors.textSecondary,
            error = appColors.error
        )
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = materialScheme,
            typography = AkrepTypography,
            content = content
        )
    }
}

private fun monochromeColors(isDark: Boolean): AppColors {
    val base = AppPalettes.paletteFor(AppThemeOption.BLACK_PEARL)
    return if (isDark) {
        base
    } else {
        base.copy(
            background = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
            backgroundGradient = androidx.compose.ui.graphics.Brush.verticalGradient(
                listOf(
                    androidx.compose.ui.graphics.Color(0xFFFFFFFF),
                    androidx.compose.ui.graphics.Color(0xFFF2F2F2)
                )
            ),
            surface = androidx.compose.ui.graphics.Color(0xFFF7F7F7),
            surfaceVariant = androidx.compose.ui.graphics.Color(0xFFECECEC),
            accent = androidx.compose.ui.graphics.Color(0xFF000000),
            accentVariant = androidx.compose.ui.graphics.Color(0xFF333333),
            onAccent = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
            textPrimary = androidx.compose.ui.graphics.Color(0xFF111111),
            textSecondary = androidx.compose.ui.graphics.Color(0xFF666666),
            divider = androidx.compose.ui.graphics.Color(0xFFDDDDDD)
        )
    }
}
