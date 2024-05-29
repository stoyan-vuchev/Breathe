package choehaualen.breath.di

import choehaualen.breath.data.local.AppDatabase
import choehaualen.breath.data.manager.SleepManager
import choehaualen.breath.data.preferences.AppPreferences
import choehaualen.breath.data.preferences.AppPreferencesImpl
import choehaualen.breath.data.preferences.AppPreferencesImpl.Companion.preferences
import choehaualen.breath.presentation.MainActivityViewModel
import choehaualen.breath.presentation.boarding.user.UserScreenViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * A DI module containing all the necessary dependencies related to the core application.
 */
val appModule = module {

    // Creates a single instance of a [AppPreferences].
    single<AppPreferences> {
        AppPreferencesImpl(
            preferences = androidContext()
                .applicationContext
                .preferences
        )
    }

    // Create a single [AppDatabase] instance.
    single<AppDatabase> {
        AppDatabase.createInstance(
            context = androidContext().applicationContext,
            inMemory = false
        )
    }

    // Create a single [SleepManager] instance with injected [SleepDao].
    single<SleepManager> {
        SleepManager(
            sleepDao = get<AppDatabase>().sleepDao,
            ioDispatcher = Dispatchers.IO
        )
    }

    // Create a single [MainActivity] instance with injected [AppPreferences].
    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            preferences = get<AppPreferences>()
        )
    }

    // Create a single [UserScreenViewModel] instance with injected [AppPreferences].
    viewModel<UserScreenViewModel> {
        UserScreenViewModel(
            preferences = get<AppPreferences>()
        )
    }

}