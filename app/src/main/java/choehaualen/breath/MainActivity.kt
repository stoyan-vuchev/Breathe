package choehaualen.breath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import choehaualen.breath.core.ui.theme.BreathTheme
import choehaualen.breath.presentation.user.UserScreen
import choehaualen.breath.presentation.user.UserScreenViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreathTheme {

                val viewModel = koinViewModel<UserScreenViewModel>()
                val screenState by viewModel.screenState.collectAsStateWithLifecycle()

                UserScreen(
                    screenState = screenState,
                    onUIAction = viewModel::onUIAction
                )

            }
        }
    }

}