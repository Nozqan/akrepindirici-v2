package com.nebiozkan.akrepindirici.security

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface BiometricResult {
    data object Success : BiometricResult
    data class Error(val message: String) : BiometricResult
    data object Cancelled : BiometricResult
}

/**
 * Wraps androidx.biometric to gate access to the app and to the locally
 * stored media gallery behind a fingerprint/face check, per the spec's
 * "App-Lock" requirement. Falls back gracefully when no biometric hardware
 * is enrolled, rather than locking the user out.
 */
@Singleton
class BiometricAuthManager @Inject constructor() {

    fun canAuthenticate(activity: FragmentActivity): Boolean {
        val manager = BiometricManager.from(activity)
        return manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) ==
            BiometricManager.BIOMETRIC_SUCCESS
    }

    fun authenticate(
        activity: FragmentActivity,
        title: String,
        subtitle: String,
        negativeButtonText: String
    ) = callbackFlow<BiometricResult> {
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                trySend(BiometricResult.Success)
                close()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                val outcome = if (errorCode == BiometricPrompt.ERROR_USER_CANCELED ||
                    errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON
                ) {
                    BiometricResult.Cancelled
                } else {
                    BiometricResult.Error(errString.toString())
                }
                trySend(outcome)
                close()
            }

            override fun onAuthenticationFailed() {
                // Single failed attempt; prompt stays open for retry, so we don't close here.
            }
        }

        val prompt = BiometricPrompt(activity, executor, callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText(negativeButtonText)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        prompt.authenticate(promptInfo)

        awaitClose { /* BiometricPrompt manages its own lifecycle teardown */ }
    }
}
