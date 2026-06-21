
package com.nebiozkan.akrepindirici.di

import android.content.Context
import androidx.room.Room
import com.nebiozkan.akrepindirici.data.local.db.AppDatabase
import com.nebiozkan.akrepindirici.data.local.db.MediaHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMediaHistoryDao(database: AppDatabase): MediaHistoryDao =
        database.mediaHistoryDao()

    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context
}
