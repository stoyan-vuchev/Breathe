package io.duckcat.d.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.duckcat.d.data.local.AppDatabase
import io.duckcat.d.data.local.dao.StudySubjectDao
import io.duckcat.d.data.preferences.AppPreferences
import io.duckcat.d.data.preferences.AppPreferencesImpl
import io.duckcat.d.data.preferences.AppPreferencesImpl.Companion.preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferencesImpl(preferences = context.applicationContext.preferences)
    }

    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideStudySubjectDao(appDatabase: AppDatabase): StudySubjectDao {
        return appDatabase.studySubjectDao
    }
}
