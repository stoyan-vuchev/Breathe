package choehaualen.breath.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import choehaualen.breath.data.local.entity.UserEntity

/**
 * A database containing [UserEntity] records with a user data.
 */
@Database(
    entities = [UserEntity::class],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {

    /** The Dao instance of the [UserDatabase]. */
    abstract val dao: UserDao

    companion object {

        /**
         * Creates a [UserDatabase] instance.
         * @param applicationContext The application context.
         * @param inMemory Whether to create a temporary (inMemory) or persistent instance.
         */
        fun createInstance(
            applicationContext: Context,
            inMemory: Boolean
        ): UserDatabase = if (inMemory) {

            // A temporary instance. Used for a TESTING purposes ONLY!
            Room.inMemoryDatabaseBuilder(
                context = applicationContext,
                klass = UserDatabase::class.java
            ).build()

        } else {

            // A persistent instance. Used for an ACTUAL implementation!
            Room.databaseBuilder(
                context = applicationContext,
                klass = UserDatabase::class.java,
                name = "user_db"
            ).build()

        }

    }

}