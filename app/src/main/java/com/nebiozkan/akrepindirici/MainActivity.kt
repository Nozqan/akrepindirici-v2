package com.nebiozkan.akrepindirici

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.nebiozkan.akrepindirici.data.repository.AppSettings
import com.nebiozkan.akrepindirici.data.repository.SettingsRepository
import com.nebiozkan.akrepindirici.security.BiometricAuthManager
import com.nebiozkan.akrepindirici.security.BiometricResult
import com.nebiozkan.akrepindirici.ui.navigation.AkrepNavHost
import com.nebiozkan.akrepindirici.ui.screens.lock.AppLockScreen
import com.nebiozkan.akrepindirici.ui.theme.AkrepIndiriciTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @Inject
    lateinit var biometricAuthManager: BiometricAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settings by settingsRepository.settings.collectAsState(initial = AppSettings())

            var isUnlocked by remember { mutableStateOf(false) }
            var lockError by remember { mutableStateOf<String?>(null) }

            AkrepIndiriciTheme(
                themeOption = settings.themeOption,
                useBlackWhiteMode = settings.blackWhiteMode
            ) {
                val needsLock = settings.biometricLockEnabled && !isUnlocked

                if (needsLock) {
                    val handleResult: (BiometricResult) -> Unit = { result ->
                        when (result) {
                            is BiometricResult.Success -> {
                                isUnlocked = true
                                lockError = null
                            }
                            is BiometricResult.Error -> lockError = result.message
                            BiometricResult.Cancelled -> { /* stay locked, user can retry */ }
                        }
                    }

                    LaunchedEffect(Unit) {
                        triggerBiometricCheck(handleResult)
                    }

                    AppLockScreen(
                        onUnlockRequested = { triggerBiometricCheck(handleResult) },
                        errorMessage = lockError
                    )
                } else {
                    AkrepNavHost()
                }
            }
        }
    }

    private fun triggerBiometricCheck(onResult: (BiometricResult) -> Unit) {
        if (!biometricAuthManager.canAuthenticate(this)) {
            // No biometric hardware/enrollment available — don't lock the user out.
            onResult(BiometricResult.Success)
            return
        }

        lifecycleScope.launch {
            biometricAuthManager.authenticate(
                activity = this@MainActivity,
                title = getString(R.string.biometric_prompt_title),
                subtitle = getString(R.string.biometric_prompt_subtitle),
                negativeButtonText = getString(R.string.biometric_prompt_negative)
            ).collectLatest(onResult)
        }
    }
}
