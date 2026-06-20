package com.nebiozkan.akrepindirici.ui.screens.common

/** Shared loading/error state used by the Home, TikTok, and Instagram import screens. */
data class CategoryUiState(
    val isImporting: Boolean = false,
    val errorMessage: String? = null
)
