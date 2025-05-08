    package io.duckcat.d.presentation.fitness

    import androidx.compose.runtime.Stable

    @Stable
    data class FitnessScreenState(
        val isSetupCompleted: Boolean = false,
        val height: Float = 0f,      // in meters
        val weight: Float = 0f,      // in kilograms
        val bmi: Float = 0f,
        val bmiCategory: String = "",
        val setupDate: Long = 0L
    )
