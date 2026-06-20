package com.nebiozkan.akrepindirici.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents one media item the user has imported and processed locally.
 *
 * IMPORTANT SCOPE NOTE: this app processes videos the user supplies
 * directly from their own device storage (via the system file/media
 * picker). It does not scrape or fetch content from third-party social
 * platforms — that requires those platforms' official APIs and a
 * developer agreement, which is out of scope for this build.
 */
@Entity(tableName = "media_history")
data class MediaHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val displayName: String,
    val sourceUri: String,
    val localFilePath: String,
    val thumbnailPath: String?,
    val durationMs: Long,
    val fileSizeBytes: Long,
    val category: String, // "home" | "tiktok" | "instagram"
    val importedAtEpochMillis: Long,
    val isHidden: Boolean = false
)
