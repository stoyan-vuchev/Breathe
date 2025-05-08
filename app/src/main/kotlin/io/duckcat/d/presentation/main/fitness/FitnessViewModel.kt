package io.duckcat.d.presentation.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duckcat.d.data.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _screenState = MutableStateFlow(FitnessScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        loadFitnessData()
    }

    private fun loadFitnessData() {
        viewModelScope.launch {
            // Load stored data from your datastore (ensure your AppPreferences implementation emits updates)
            val heightResult = appPreferences.getFitnessHeight() // e.g. Flow<Float>
            val weightResult = appPreferences.getFitnessWeight()
            val bmiResult = appPreferences.getFitnessBMI()
            val bmiCategoryResult = appPreferences.getFitnessBMICategory()

            // For simplicity, use firstOrNull (in production, you might want to collect these flows)
            val height = heightResult.firstOrNull() ?: 0f
            val weight = weightResult.firstOrNull() ?: 0f
            val bmi = bmiResult.firstOrNull() ?: 0f
            val category = bmiCategoryResult.firstOrNull() ?: ""
            if (height > 0 && weight > 0 && bmi > 0 && category.isNotEmpty()) {
                _screenState.value = FitnessScreenState(
                    isSetupCompleted = true,
                    height = height,
                    weight = weight,
                    bmi = bmi,
                    bmiCategory = category,
                    setupDate = System.currentTimeMillis() // update as needed
                )
            }
        }
    }

    fun onSubmitFitnessData(height: Float, weight: Float) {
        viewModelScope.launch {
            // Calculate BMI (using height in m)
            val bmi = weight / (height * height)
            val category = when {
                bmi <= 18.5f -> "Underweight"
                bmi <= 25.0f -> "Normal"
                bmi <= 30.0f -> "Overweight"
                else -> "Obesity"
            }

            // Save new data to datastore via AppPreferences.
            appPreferences.setFitnessHeight(height)
            appPreferences.setFitnessWeight(weight)
            appPreferences.setFitnessBMI(bmi)
            appPreferences.setFitnessBMICategory(category)
            // Immediately update the local screen state.
            _screenState.value = FitnessScreenState(
                isSetupCompleted = true,
                height = height,
                weight = weight,
                bmi = bmi,
                bmiCategory = category,
                setupDate = System.currentTimeMillis() // update date if needed
            )
        }
    }
}
