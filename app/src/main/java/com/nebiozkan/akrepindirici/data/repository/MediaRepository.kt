package com.nebiozkan.akrepindirici.data.repository

import com.nebiozkan.akrepindirici.data.local.db.MediaHistoryDao
import com.nebiozkan.akrepindirici.data.local.entity.MediaHistoryEntity
import com.nebiozkan.akrepindirici.domain.model.MediaCategory
import com.nebiozkan.akrepindirici.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for processed media. Wraps Room and maps entities
 * to domain models so the UI layer never touches persistence types directly.
 */
@Singleton
class MediaRepository @Inject constructor(
    private val dao: MediaHistoryDao
) {
    fun observeByCategory(category: MediaCategory): Flow<List<MediaItem>> =
        dao.observeByCategory(category.storageKey).map { list -> list.map { it.toDomain() } }

    fun observeAll(): Flow<List<MediaItem>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    suspend fun save(item: MediaItem): Long = dao.insert(item.toEntity())

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun clearAll() = dao.clearAll()

    private fun MediaHistoryEntity.toDomain() = MediaItem(
        id = id,
        displayName = displayName,
        localFilePath = localFilePath,
        thumbnailPath = thumbnailPath,
        durationMs = durationMs,
        fileSizeBytes = fileSizeBytes,
        category = MediaCategory.fromKey(category),
        importedAtEpochMillis = importedAtEpochMillis
    )

    private fun MediaItem.toEntity() = MediaHistoryEntity(
        id = id,
        displayName = displayName,
        sourceUri = localFilePath,
        localFilePath = localFilePath,
        thumbnailPath = thumbnailPath,
        durationMs = durationMs,
        fileSizeBytes = fileSizeBytes,
        category = category.storageKey,
        importedAtEpochMillis = importedAtEpochMillis
    )
}
