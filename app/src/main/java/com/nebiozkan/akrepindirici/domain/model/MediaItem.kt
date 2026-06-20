package com.nebiozkan.akrepindirici.domain.model

/** UI-facing category, mirrors the three bottom-nav destinations. */
enum class MediaCategory(val storageKey: String) {
    HOME("home"),
    TIKTOK("tiktok"),
    INSTAGRAM("instagram");

    companion object {
        fun fromKey(key: String): MediaCategory =
            entries.firstOrNull { it.storageKey == key } ?: HOME
    }
}

/** Clean domain representation of a processed media item, decoupled from the Room entity. */
data class MediaItem(
    val id: Long,
    val displayName: String,
    val localFilePath: String,
    val thumbnailPath: String?,
    val durationMs: Long,
    val fileSizeBytes: Long,
    val category: MediaCategory,
    val importedAtEpochMillis: Long
)
