package io.duckcat.d.presentation.study

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.duckcat.d.data.local.entity.StudySubjectEntity

@Composable
fun StudyPlanerScreen(
    viewModel: StudyPlanerViewModel = hiltViewModel(),
    onEditSubject: (StudySubjectEntity) -> Unit = {}
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    if (!screenState.isSetupComplete) {
        StudyPlanerSetupScreen(
            onAddSubject = { name, priority, examDate ->
                viewModel.addSubject(name, priority, examDate)
            },
            onDone = {
                // Mark setup as complete.
                viewModel.markSetupComplete()
            }
        )
    } else {
        StudyPlanerMainScreen(
            subjects = screenState.subjects,
            currentFocusSubject = viewModel.currentFocusSubject,
            onEditSubject = onEditSubject
        )
    }
}
