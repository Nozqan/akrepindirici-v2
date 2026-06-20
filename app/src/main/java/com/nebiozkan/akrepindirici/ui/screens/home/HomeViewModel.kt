package com.nebiozkan.akrepindirici.ui.screens.home

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nebiozkan.akrepindirici.data.repository.MediaRepository
import com.nebiozkan.akrepindirici.domain.model.MediaCategory
import com.nebiozkan.akrepindirici.domain.model.MediaItem
import com.nebiozkan.akrepindirici.ui.screens.common.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias HomeUiState = CategoryUiState

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    val historyItems: StateFlow<List<MediaItem>> =
        mediaRepository.observeByCategory(MediaCategory.HOME).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /**
     * Registers a video the user picked from their own device via the
     * system document/media picker. We only read display metadata here;
     * actual file copy + thumbnail generation happens in a follow-up step
     * once a MediaStore/SAF-backed import pipeline is wired in.
     */
    fun onVideoPicked(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isImporting = true, errorMessage = null)
            try {
                val displayName = queryDisplayName(uri, contentResolver) ?: "Video"
                mediaRepository.save(
                    MediaItem(
                        id = 0,
                        displayName = displayName,
                        localFilePath = uri.toString(),
                        thumbnailPath = null,
                        durationMs = 0L,
                        fileSizeBytes = 0L,
                        category = MediaCategory.HOME,
                        importedAtEpochMillis = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isImporting = false)
            }
        }
    }

    private fun queryDisplayName(uri: Uri, resolver: ContentResolver): String? {
        val cursor = resolver.query(uri, null, null, null, null) ?: return null
        cursor.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex >= 0) {
                return it.getString(nameIndex)
            }
        }
        return null
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch { mediaRepository.deleteById(id) }
    }
}
