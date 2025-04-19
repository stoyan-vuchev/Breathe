package io.duckcat.d.presentation.study

import io.duckcat.d.data.local.entity.StudySubjectEntity

data class StudyPlanerScreenState(
    val isSetupComplete: Boolean = false,
    val subjects: List<StudySubjectEntity> = emptyList()
)
