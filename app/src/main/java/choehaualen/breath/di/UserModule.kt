package choehaualen.breath.di

import choehaualen.breath.data.local.UserDatabase
import choehaualen.breath.presentation.user.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * A DI module containing all the necessary dependencies related to user data.
 */
val userModule = module {

    // Creates a single instance of a [UserDatabase].
    single<UserDatabase> {
        UserDatabase.createInstance(
            applicationContext = androidApplication().applicationContext,
            inMemory = false
        )
    }

    // Creates a single instance of a [UserViewModel].
    viewModel<UserViewModel> {
        UserViewModel(dao = get<UserDatabase>().dao)
    }

}