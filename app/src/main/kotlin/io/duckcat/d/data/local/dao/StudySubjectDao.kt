package io.duckcat.d.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.duckcat.d.data.local.entity.StudySubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySubjectDao {

    @Query("SELECT * FROM study_subjects ORDER BY examDate ASC, priority DESC")
    fun getAllSubjects(): Flow<List<StudySubjectEntity>>  // ❌ Removed `suspend` from Flow return

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: StudySubjectEntity)

    @Update
    suspend fun updateSubject(subject: StudySubjectEntity)

    @Delete
    suspend fun deleteSubject(subject: StudySubjectEntity)

    @Query("DELETE FROM study_subjects")
    suspend fun deleteAllSubjects()  // ✅ New function to delete all data
}

