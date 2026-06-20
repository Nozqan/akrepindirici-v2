package com.nebiozkan.akrepindirici.ui.screens.lock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nebiozkan.akrepindirici.R
import com.nebiozkan.akrepindirici.ui.components.LiveActionButton
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors

/**
 * Full-screen gate shown on launch when the user has enabled "Parmak İzi
 * Kilidi" in Settings. The actual biometric prompt is triggered by
 * MainActivity (which owns the FragmentActivity needed by BiometricPrompt);
 * this screen just presents the locked state and a retry affordance.
 */
@Composable
fun AppLockScreen(
    onUnlockRequested: () -> Unit,
    errorMessage: String?
) {
    val colors = LocalAppColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundGradient)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Fingerprint,
            contentDescription = null,
            tint = colors.accent,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Text(
            text = stringResource(R.string.biometric_prompt_title),
            style = MaterialTheme.typography.headlineMedium,
            color = colors.textPrimary
        )
        Text(
            text = stringResource(R.string.biometric_prompt_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            modifier = Modifier.padding(top = 8.dp, bottom = 28.dp)
        )

        errorMessage?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        LiveActionButton(
            text = stringResource(R.string.biometric_retry),
            onClick = onUnlockRequested,
            modifier = Modifier.fillMaxWidth(),
            accentBrush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                listOf(colors.accent, colors.accentVariant)
            ),
            contentColor = colors.onAccent
        )
    }
}
