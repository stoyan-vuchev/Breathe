package io.proxima.breathe.presentation.fitness

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.proxima.breathe.data.preferences.AppPreferences
import io.proxima.breathe.core.etc.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

// Note: In your AppPreferences interface you need to add functions like:
// suspend fun setFitnessHeight(height: Float)
// fun getFitnessHeight(): Flow<Float>
// … and similarly for weight, BMI, and BMI category.
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
            // Try to load stored fitness data from datastore.
            // (Assumes that appPreferences has been extended with the following functions.)
            val heightResult = appPreferences.getFitnessHeight() // e.g. Flow<Float>
            val weightResult = appPreferences.getFitnessWeight()
            val bmiResult = appPreferences.getFitnessBMI()
            val bmiCategoryResult = appPreferences.getFitnessBMICategory()
            // For simplicity, assume you get a value immediately:
            // (In production you may need to collect or use firstOrNull on these flows.)
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
                    bmiCategory = category
                )
            }
        }
    }

    fun onSubmitFitnessData(height: Float, weight: Float) {
        viewModelScope.launch {
            // Calculate BMI: weight / (height * height)
            val bmi = weight / (height * height)
            val category = when {
                bmi < 18.5f -> "Underweight"
                bmi < 25f -> "Normal"
                bmi < 30f -> "Overweight"
                else -> "Obesity"
            }
            // Save data to datastore via AppPreferences:
            appPreferences.setFitnessHeight(height)
            appPreferences.setFitnessWeight(weight)
            appPreferences.setFitnessBMI(bmi)
            appPreferences.setFitnessBMICategory(category)
            _screenState.value = FitnessScreenState(
                isSetupCompleted = true,
                height = height,
                weight = weight,
                bmi = bmi,
                bmiCategory = category
            )
        }
    }
}
