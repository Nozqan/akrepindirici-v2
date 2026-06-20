package com.nebiozkan.akrepindirici.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebiozkan.akrepindirici.data.repository.AppSettings
import com.nebiozkan.akrepindirici.data.repository.SettingsRepository
import com.nebiozkan.akrepindirici.ui.theme.AppThemeOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settings: StateFlow<AppSettings> = settingsRepository.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppSettings()
    )

    fun onThemeSelected(option: AppThemeOption) {
        viewModelScope.launch { settingsRepository.setTheme(option) }
    }

    fun onBlackWhiteModeToggled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setBlackWhiteMode(enabled) }
    }

    fun onBiometricLockToggled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setBiometricLockEnabled(enabled) }
    }
}
