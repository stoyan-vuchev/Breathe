package io.proxima.breathe.data.local.dao

import androidx.room.*
import io.proxima.breathe.data.local.entity.StudySubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySubjectDao {
    @Query("SELECT * FROM study_subjects ORDER BY examDate ASC, priority DESC")
    fun getAllSubjects(): Flow<List<StudySubjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: StudySubjectEntity)

    @Update
    suspend fun updateSubject(subject: StudySubjectEntity)

    @Delete
    suspend fun deleteSubject(subject: StudySubjectEntity)
}
