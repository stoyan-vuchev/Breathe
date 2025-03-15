package io.proxima.breathe

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltBreatheApp : BreatheApp(), Configuration.Provider {

    /*@Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .setExecutor(Dispatchers.Default.asExecutor())
            .build()


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ACRA.init(
            this, CoreConfigurationBuilder()
                .withBuildConfigClass(BuildConfig::class.java)
                .withReportFormat(StringFormat.JSON)
                .withPluginConfigurations(

                    // Dialog configuration:
                    DialogConfigurationBuilder()
                        .withText(getString(R.string.dialog_text))
                        .withTitle(getString(R.string.dialog_title))
                        .withPositiveButtonText(getString(R.string.dialog_positive))
                        .withNegativeButtonText(getString(R.string.dialog_negative))
                        .build(),

                    // Mail sender configuration:
                    MailSenderConfigurationBuilder()
                        .withMailTo(Constants.EMAIL)
                        .withReportFileName("crash_report.txt")
                        .withReportAsFile(true)
                        .build()
                )
        )
    }

    override fun onCreate() {
        super.onCreate()

        initWorker()
    }

    private fun initWorker() {
        val maxTimeSec = LocalTime.MAX.toSecondOfDay() + 1
        val currentTimeSec = LocalTime.now().toSecondOfDay()

        val delay = (maxTimeSec - currentTimeSec)
        if (delay > 0) {
            startRepeatWorker(delay)
        }
    }

    private fun startRepeatWorker(delay: Int) {
        // repeat task request
        val workRequest =
            PeriodicWorkRequest.Builder(RepeatTaskWorker::class.java, 1, TimeUnit.DAYS)
                .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "Repeat-Tasks",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
*/



    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

}

open class BreatheApp : Application()