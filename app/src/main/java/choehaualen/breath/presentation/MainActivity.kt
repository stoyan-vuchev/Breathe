package choehaualen.breath.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import choehaualen.breath.core.ui.theme.BreathTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = koinViewModel<MainActivityViewModel>()
            val isUserAuthenticated by viewModel.isUserAuthenticated.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            BreathTheme {

                AppNavigationHost(
                    navController = navController,
                    isUserAuthenticated = isUserAuthenticated,
                    onAuthenticateUser = viewModel::onAuthenticateUser
                )

            }

        }
    }

}