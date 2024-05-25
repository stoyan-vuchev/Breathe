package choehaualen.breath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.setup.SetupNavigationDestinations
import choehaualen.breath.presentation.setup.setupNavigationGraph
import choehaualen.breath.presentation.setup.welcome.SetupScreen
import choehaualen.breath.presentation.setup.welcome.SetupScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            BreathTheme {

                NavHost(
                    navController = navController,
                    startDestination = SetupNavigationDestinations.NAV_ROUTE
                ) {

                    setupNavigationGraph(navHostController = navController)

                }

            }
        }
    }

}