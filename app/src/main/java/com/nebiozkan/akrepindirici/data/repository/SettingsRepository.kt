package com.nebiozkan.akrepindirici.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nebiozkan.akrepindirici.ui.theme.AppThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "akrep_settings")

data class AppSettings(
    val themeOption: AppThemeOption = AppThemeOption.BLACK_PEARL,
    val blackWhiteMode: Boolean = false,
    val biometricLockEnabled: Boolean = false
)

@Singleton
class SettingsRepository @Inject constructor(
    private val context: Context
) {
    private object Keys {
        val THEME = stringPreferencesKey("theme_option")
        val BLACK_WHITE_MODE = booleanPreferencesKey("black_white_mode")
        val BIOMETRIC_LOCK = booleanPreferencesKey("biometric_lock_enabled")
    }

    val settings: Flow<AppSettings> = context.dataStore.data.map { prefs ->
        AppSettings(
            themeOption = AppThemeOption.fromKey(prefs[Keys.THEME]),
            blackWhiteMode = prefs[Keys.BLACK_WHITE_MODE] ?: false,
            biometricLockEnabled = prefs[Keys.BIOMETRIC_LOCK] ?: false
        )
    }

    suspend fun setTheme(option: AppThemeOption) {
        context.dataStore.edit { it[Keys.THEME] = option.storageKey }
    }

    suspend fun setBlackWhiteMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.BLACK_WHITE_MODE] = enabled }
    }

    suspend fun setBiometricLockEnabled(enabled: Boolean) {
        context.dataStore.edit { it[Keys.BIOMETRIC_LOCK] = enabled }
    }
}
