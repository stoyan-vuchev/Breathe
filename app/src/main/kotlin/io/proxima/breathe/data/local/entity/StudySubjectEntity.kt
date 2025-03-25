package io.proxima.breathe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_subjects")
data class StudySubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectName: String,
    val priority: Int,
    // examDate stored as epoch milliseconds
    val examDate: Long
)
