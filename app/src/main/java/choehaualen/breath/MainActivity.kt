package choehaualen.breath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.welcome.WelcomeScreen
import choehaualen.breath.presentation.welcome.WelcomeScreenViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreathTheme {

                val viewModel = viewModel<WelcomeScreenViewModel>()
                val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                WelcomeScreen(
                    screenState = screenState,
                    onUIAction = viewModel::onUIAction
                )

            }
        }
    }

}