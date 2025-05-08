package io.duckcat.d.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.duckcat.d.core.ui.theme.BreathTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()


        setContent {


            val viewModel = hiltViewModel<MainActivityViewModel>()
            val isUserAuthenticated by viewModel.isUserAuthenticated.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            BreathTheme {

                AppNavigationHost(
                    navController = navController,
                    isUserAuthenticated = isUserAuthenticated,
                    onAuthenticateUser = viewModel::onAuthenticateUser,
                    onRecreateActivity = {
                        viewModel.onAuthenticateUser()
                        finishAffinity()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                )

            }

        }


    }

}