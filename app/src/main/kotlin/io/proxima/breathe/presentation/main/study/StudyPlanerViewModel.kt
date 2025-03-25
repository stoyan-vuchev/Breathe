package io.proxima.breathe.presentation.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.data.local.dao.StudySubjectDao
import io.proxima.breathe.data.local.entity.StudySubjectEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyPlanerViewModel @Inject constructor(
    private val studySubjectDao: StudySubjectDao
) : ViewModel() {

    // Expose subjects as a StateFlow
    val subjectsFlow: StateFlow<List<StudySubjectEntity>> = studySubjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addSubject(subjectName: String, priority: Int, examDate: Long) {
        viewModelScope.launch {
            val subject = StudySubjectEntity(
                subjectName = subjectName,
                priority = priority,
                examDate = examDate
            )
            studySubjectDao.insertSubject(subject)
        }
    }

    fun updateSubject(subject: StudySubjectEntity) {
        viewModelScope.launch {
            studySubjectDao.updateSubject(subject)
        }
    }

    // For simplicity, we choose the subject with the earliest exam date as the focus.
    val currentFocusSubject: StudySubjectEntity?
        get() = subjectsFlow.value.minByOrNull { it.examDate }
}
