package choehaualen.breath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import choehaualen.breath.core.ui.navigateSingleTop
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.data.preferences.AppPreferences
import choehaualen.breath.presentation.boarding.BoardingNavigationDestination
import choehaualen.breath.presentation.boarding.boardingNavigationGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = koinViewModel<MainActivityViewModel>()
            val navController = rememberNavController()

            BreathTheme {

                NavHost(
                    navController = navController,
                    startDestination = "start_route"
                ) {

                    composable(
                        route = "start_route",
                        content = {

                            LaunchedEffect(viewModel.isUserSetFlow) {
                                viewModel.isUserSetFlow.collectLatest { isUserSet ->
                                    isUserSet?.let { isSet ->
                                        navController.navigateSingleTop(
                                            route = if (isSet) "main_screen_route"
                                            else BoardingNavigationDestination.Welcome.route
                                        )
                                    }
                                }
                            }

                        }
                    )

                    boardingNavigationGraph(navController = navController)

                    composable(
                        route = "main_screen_route",
                        content = {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(BreathTheme.colors.background),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(text = "Authenticated!")

                            }

                        }
                    )

                }

            }

        }
    }

}

class MainActivityViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _isUserSetChannel = Channel<Boolean?>()
    val isUserSetFlow = _isUserSetChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val username = withContext(Dispatchers.IO) { preferences.getUser() }
            sendEvent(username != null)
        }
    }

    private fun sendEvent(isUserSet: Boolean) {
        viewModelScope.launch { _isUserSetChannel.send(isUserSet) }
    }

}