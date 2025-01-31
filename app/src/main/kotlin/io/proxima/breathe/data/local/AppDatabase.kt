package io.proxima.breathe.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.local.dao.SleepDao
import io.proxima.breathe.data.local.entity.QuoteEntity
import io.proxima.breathe.data.local.entity.SleepEntity

@Database(
    entities = [SleepEntity::class, QuoteEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val sleepDao: SleepDao
    abstract val quotesDao: QuotesDao

    companion object {

        fun createInstance(
            context: Context,
            inMemory: Boolean
        ) = if (inMemory) {

            // Use inMemory only for testing!

            Room.inMemoryDatabaseBuilder(
                context = context,
                klass = AppDatabase::class.java
            ).build()

        } else {

            // Use non-inMemory for actual implementation.

            Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "app_db"
            ).build()

        }

    }

}