package com.nebiozkan.akrepindirici.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nebiozkan.akrepindirici.data.local.entity.MediaHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaHistoryDao {

    @Query("SELECT * FROM media_history WHERE category = :category ORDER BY importedAtEpochMillis DESC")
    fun observeByCategory(category: String): Flow<List<MediaHistoryEntity>>

    @Query("SELECT * FROM media_history ORDER BY importedAtEpochMillis DESC")
    fun observeAll(): Flow<List<MediaHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MediaHistoryEntity): Long

    @Delete
    suspend fun delete(entity: MediaHistoryEntity)

    @Query("DELETE FROM media_history WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM media_history")
    suspend fun clearAll()
}
