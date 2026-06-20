package com.nebiozkan.akrepindirici.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Provides the active [AppColors] palette down the composition tree.
 * Every screen and component reads colors from this local instead of
 * hardcoding values, so a single state change in SettingsViewModel
 * recolors the entire app — backgrounds, buttons, accents, dividers.
 */
val LocalAppColors = staticCompositionLocalOf { AppPalettes.paletteFor(AppThemeOption.BLACK_PEARL) }
