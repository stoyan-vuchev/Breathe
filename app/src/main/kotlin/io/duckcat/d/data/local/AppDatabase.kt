package io.duckcat.d.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.duckcat.d.data.local.dao.QuotesDao
import io.duckcat.d.data.local.dao.StudySubjectDao
import io.duckcat.d.data.local.entity.QuoteEntity
import io.duckcat.d.data.local.entity.StudySubjectEntity


@Database(
    entities = [ QuoteEntity::class, StudySubjectEntity::class],
    version = 6,
    exportSchema = false,
    //autoMigrations = [AutoMigration(from = 5, to = 6)]
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

