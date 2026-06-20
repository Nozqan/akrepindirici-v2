package com.nebiozkan.akrepindirici.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nebiozkan.akrepindirici.data.local.entity.MediaHistoryEntity

@Database(
    entities = [MediaHistoryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaHistoryDao(): MediaHistoryDao

    companion object {
        const val DATABASE_NAME = "akrep_indirici.db"
    }
}
