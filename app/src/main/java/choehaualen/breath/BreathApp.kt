package choehaualen.breath

import android.app.Application
import choehaualen.breath.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BreathApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {

            // Provide the Application context.
            androidContext(this@BreathApp)

            // Install the [UserModule].
            modules(userModule)

        }
    }

}