package com.nebiozkan.akrepindirici.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Identifies each of the five selectable theme palettes described in the
 * product spec. Persisted by key (see [AppThemeOption.storageKey]) so the
 * stored preference is stable even if display order changes.
 */
enum class AppThemeOption(val storageKey: String) {
    BLACK_PEARL("black_pearl"),
    MIDNIGHT_BLUE("midnight_blue"),
    EMERALD("emerald"),
    RUBY("ruby"),
    FOUNDER_SPECIAL("founder_special");

    companion object {
        fun fromKey(key: String?): AppThemeOption =
            entries.firstOrNull { it.storageKey == key } ?: BLACK_PEARL
    }
}

/**
 * A complete color + gradient definition for one theme. Every screen reads
 * colors exclusively from the [AppColors] CompositionLocal (see
 * LocalAppColors.kt) so switching themes recolors buttons, backgrounds,
 * accents, and surfaces everywhere at once.
 */
data class AppColors(
    val option: AppThemeOption,
    val background: Color,
    val backgroundGradient: Brush,
    val surface: Color,
    val surfaceVariant: Color,
    val accent: Color,
    val accentVariant: Color,
    val onAccent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val divider: Color,
    val success: Color,
    val error: Color
)

object AppPalettes {

    private val blackPearl = AppColors(
        option = AppThemeOption.BLACK_PEARL,
        background = Color(0xFF000000),
        backgroundGradient = Brush.verticalGradient(
            listOf(Color(0xFF0A0A0A), Color(0xFF000000))
        ),
        surface = Color(0xFF121212),
        surfaceVariant = Color(0xFF1C1C1C),
        accent = Color(0xFFFF8A00),
        accentVariant = Color(0xFFFFB347),
        onAccent = Color(0xFF000000),
        textPrimary = Color(0xFFF5F5F5),
        textSecondary = Color(0xFFA0A0A0),
        divider = Color(0xFF2A2A2A),
        success = Color(0xFF34D399),
        error = Color(0xFFEF4444)
    )

    private val midnightBlue = AppColors(
        option = AppThemeOption.MIDNIGHT_BLUE,
        background = Color(0xFF060B18),
        backgroundGradient = Brush.verticalGradient(
            listOf(Color(0xFF0B1530), Color(0xFF03060F))
        ),
        surface = Color(0xFF101B33),
        surfaceVariant = Color(0xFF16223F),
        accent = Color(0xFF4D8DFF),
        accentVariant = Color(0xFF7FB1FF),
        onAccent = Color(0xFF02040A),
        textPrimary = Color(0xFFEAF1FF),
        textSecondary = Color(0xFF8FA3C7),
        divider = Color(0xFF21304F),
        success = Color(0xFF34D399),
        error = Color(0xFFFF6B6B)
    )

    private val emerald = AppColors(
        option = AppThemeOption.EMERALD,
        background = Color(0xFF051712),
        backgroundGradient = Brush.verticalGradient(
            listOf(Color(0xFF0A2A20), Color(0xFF03100C))
        ),
        surface = Color(0xFF0E241C),
        surfaceVariant = Color(0xFF143226),
        accent = Color(0xFF2ECC8F),
        accentVariant = Color(0xFF6EE7B7),
        onAccent = Color(0xFF02140D),
        textPrimary = Color(0xFFE7FBF3),
        textSecondary = Color(0xFF8FBFAA),
        divider = Color(0xFF1B3A2C),
        success = Color(0xFF34D399),
        error = Color(0xFFFF6B6B)
    )

    private val ruby = AppColors(
        option = AppThemeOption.RUBY,
        background = Color(0xFF1A0508),
        backgroundGradient = Brush.verticalGradient(
            listOf(Color(0xFF330810), Color(0xFF120004))
        ),
        surface = Color(0xFF270A10),
        surfaceVariant = Color(0xFF35121A),
        accent = Color(0xFFE63950),
        accentVariant = Color(0xFFFF7A8A),
        onAccent = Color(0xFF1A0508),
        textPrimary = Color(0xFFFCEAEC),
        textSecondary = Color(0xFFC68E96),
        divider = Color(0xFF441922),
        success = Color(0xFF34D399),
        error = Color(0xFFFF4D4D)
    )

    /** "Nebi Özkan Özel" — the founder's signature gold-on-black palette. */
    private val founderSpecial = AppColors(
        option = AppThemeOption.FOUNDER_SPECIAL,
        background = Color(0xFF0A0700),
        backgroundGradient = Brush.verticalGradient(
            listOf(Color(0xFF1A1404), Color(0xFF000000))
        ),
        surface = Color(0xFF161200),
        surfaceVariant = Color(0xFF211B05),
        accent = Color(0xFFFFC727),
        accentVariant = Color(0xFFFFE08A),
        onAccent = Color(0xFF1A1400),
        textPrimary = Color(0xFFFFF8E1),
        textSecondary = Color(0xFFC9B97E),
        divider = Color(0xFF332B0A),
        success = Color(0xFF34D399),
        error = Color(0xFFFF6B6B)
    )

    fun paletteFor(option: AppThemeOption): AppColors = when (option) {
        AppThemeOption.BLACK_PEARL -> blackPearl
        AppThemeOption.MIDNIGHT_BLUE -> midnightBlue
        AppThemeOption.EMERALD -> emerald
        AppThemeOption.RUBY -> ruby
        AppThemeOption.FOUNDER_SPECIAL -> founderSpecial
    }

    val all: List<AppColors> = AppThemeOption.entries.map(::paletteFor)
}
