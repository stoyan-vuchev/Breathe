package io.proxima.breathe.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.local.dao.SleepDao
import io.proxima.breathe.data.local.dao.TaskDao
import io.proxima.breathe.data.local.entity.QuoteEntity
import io.proxima.breathe.data.local.entity.SleepEntity
import io.proxima.breathe.data.local.entity.Task
import io.proxima.breathe.data.local.dao.StudySubjectDao
import io.proxima.breathe.data.local.entity.StudySubjectEntity

@Database(
    entities = [SleepEntity::class, QuoteEntity::class, Task::class, StudySubjectEntity::class],
    version = 4,
    exportSchema = true,
    //autoMigrations = [AutoMigration(from = 3, to = 4)]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val sleepDao: SleepDao
    abstract val quotesDao: QuotesDao
    abstract val taskDao: TaskDao
    abstract fun studySubjectDao(): StudySubjectDao

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