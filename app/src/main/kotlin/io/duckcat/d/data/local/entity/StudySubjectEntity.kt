package io.duckcat.d.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_subjects")
data class StudySubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectName: String,
    val priority: Int,
    val examDate: Long
)
