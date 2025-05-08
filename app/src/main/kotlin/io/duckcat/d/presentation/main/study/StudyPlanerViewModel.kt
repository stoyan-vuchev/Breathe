package io.duckcat.d.presentation.study

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duckcat.d.data.local.dao.StudySubjectDao
import io.duckcat.d.data.local.entity.StudySubjectEntity
import io.duckcat.d.data.preferences.AppPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyPlanerViewModel @Inject constructor(
    private val studySubjectDao: StudySubjectDao,
    private val appPreferences: AppPreferences
) : ViewModel() {

    // Create a screen state flow that combines subjects and the persistent flag.
    val screenState: StateFlow<StudyPlanerScreenState> = combine(
        studySubjectDao.getAllSubjects(),
        appPreferences.getStudySetupComplete()
    ) { subjects, setupCompleteFlag ->
        StudyPlanerScreenState(
            // CORRECTED: Use OR to persist setup state even if subjects list is temporarily empty
            isSetupComplete = setupCompleteFlag || subjects.isNotEmpty(),
            subjects = subjects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StudyPlanerScreenState()
    )

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

    val currentFocusSubject: StudySubjectEntity?
        get() = screenState.value.subjects.minByOrNull { it.examDate }

    fun markSetupComplete() {
        viewModelScope.launch {
            appPreferences.setStudySetupComplete(true)
        }
    }
}