package com.nebiozkan.akrepindirici.ui.screens.instagram

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nebiozkan.akrepindirici.ui.components.LiveActionButton
import com.nebiozkan.akrepindirici.ui.components.MediaHistoryGrid
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors

@Composable
fun InstagramScreen(
    viewModel: InstagramViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val uiState by viewModel.uiState.collectAsState()
    val historyItems by viewModel.historyItems.collectAsState()
    val context = LocalContext.current

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onVideoPicked(it, context.contentResolver) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundGradient)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Instagram",
            style = MaterialTheme.typography.headlineLarge,
            color = colors.textPrimary,
            modifier = Modifier.padding(top = 24.dp)
        )
        Text(
            text = "Cihazınızdaki Instagram videolarını içe aktarın ve düzenleyin",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        LiveActionButton(
            text = "Video İçe Aktar",
            onClick = { pickVideoLauncher.launch("video/*") },
            modifier = Modifier.fillMaxWidth(),
            accentBrush = Brush.horizontalGradient(listOf(colors.accent, colors.accentVariant)),
            contentColor = colors.onAccent,
            enabled = !uiState.isImporting
        )

        if (uiState.isImporting) {
            CircularProgressIndicator(
                color = colors.accent,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Text(
            text = "Geçmiş",
            style = MaterialTheme.typography.titleMedium,
            color = colors.textPrimary,
            modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
        )

        MediaHistoryGrid(
            items = historyItems,
            onItemClick = { /* TODO: open player */ },
            onItemLongPress = { viewModel.deleteItem(it.id) },
            modifier = Modifier.fillMaxSize(),
            emptyContent = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PhotoCamera,
                        contentDescription = null,
                        tint = colors.textSecondary.copy(alpha = 0.4f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Henüz Instagram videosu içe aktarılmadı",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary
                    )
                }
            }
        )
    }
}
