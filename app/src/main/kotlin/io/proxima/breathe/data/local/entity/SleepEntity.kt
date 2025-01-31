package io.proxima.breathe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_table")
data class SleepEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    val sleepStartTimestamp: Long,
    val sleepEndTimestamp: Long,
    val totalSleepDuration: Long? = null

)