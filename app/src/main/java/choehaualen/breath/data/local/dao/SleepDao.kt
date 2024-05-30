package choehaualen.breath.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import choehaualen.breath.data.local.entity.SleepEntity

@Dao
interface SleepDao {

    @Insert
    suspend fun insertSleepEntities(sleepEntities: List<SleepEntity>)

    @Query("SELECT * FROM sleep_table WHERE sleepStartTimestamp = :sleepStartTimestamp")
    suspend fun getSleepEntity(sleepStartTimestamp: Long): SleepEntity?

    @Query("SELECT * FROM sleep_table")
    suspend fun getAllSleepEntities(): List<SleepEntity>?

    @Delete
    suspend fun deleteSleepEntity(sleepEntity: SleepEntity)

    @Query("DELETE FROM sleep_table")
    suspend fun deleteAllSleepData()

}