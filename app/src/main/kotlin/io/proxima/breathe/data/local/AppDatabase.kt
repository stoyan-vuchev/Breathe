package io.proxima.breathe.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.proxima.breathe.data.local.dao.QuotesDao
import io.proxima.breathe.data.local.dao.StudySubjectDao
import io.proxima.breathe.data.local.entity.QuoteEntity
import io.proxima.breathe.data.local.entity.StudySubjectEntity


@Database(
    entities = [ QuoteEntity::class, StudySubjectEntity::class],
    version = 6,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 5, to = 6)]
)
abstract class AppDatabase : RoomDatabase() {

//    abstract val sleepDao: SleepDao  // ✅ Property without ()
    abstract val quotesDao: QuotesDao
  //  abstract val taskDao: TaskDao
    abstract val studySubjectDao: StudySubjectDao  // ✅ Now consistent


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun createInstance(context: Context, useInMemory: Boolean = false): AppDatabase {
            return if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                    .allowMainThreadQueries() // ⚠️ Only for testing
                    .build()
            } else {
                getInstance(context)
            }
        }
    }
}

