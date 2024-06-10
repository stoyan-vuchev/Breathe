package choehaualen.breath.presentation.main.productivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductivityScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ProductivityScreenState())
    val state = _state.asStateFlow()

    private val _uiActionChannel = Channel<ProductivityScreenUIAction>()
    val uiActionFlow = _uiActionChannel.receiveAsFlow()

    fun onUIAction(uiAction: ProductivityScreenUIAction) = when (uiAction) {
        is ProductivityScreenUIAction.NavigateUp -> sendUIAction(uiAction)
        else -> Unit
    }

    private fun sendUIAction(uiAction: ProductivityScreenUIAction) {
        viewModelScope.launch { _uiActionChannel.send(uiAction) }
    }

}