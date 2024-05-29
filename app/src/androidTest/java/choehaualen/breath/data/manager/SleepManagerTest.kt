package choehaualen.breath.data.manager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import choehaualen.breath.core.etc.Result
import choehaualen.breath.data.local.AppDatabase
import choehaualen.breath.data.local.entity.SleepEntity
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SleepManagerTest {

    private lateinit var db: AppDatabase
    private lateinit var sleepManager: SleepManager

    private val scheduler = TestCoroutineScheduler()
    private val testCoroutineDispatcher = StandardTestDispatcher(scheduler)

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()


        db = AppDatabase.createInstance(
            context = context.applicationContext,
            inMemory = true
        )

        sleepManager = SleepManager(
            sleepDao = db.sleepDao,
            ioDispatcher = testCoroutineDispatcher
        )

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun save_and_read_sleep_data() = runTest(testCoroutineDispatcher) {

        val expected = Result.Success(
            listOf(
                SleepEntity(
                    id = 1L,
                    sleepStartTimestamp = 0L,
                    sleepEndTimestamp = 1L
                )
            )
        )

        sleepManager.saveSleepData(expected.data!!.first())

        when (val actual = sleepManager.readSleepData()) {
            is Result.Success -> assertThat(actual.data).isEqualTo(expected.data)
            is Result.Error -> throw Exception(actual.error.toString())
        }

    }

    @Test
    fun save_and_delete_all_sleep_data() = runTest(testCoroutineDispatcher) {

        val sleepList = listOf(
            SleepEntity(
                sleepStartTimestamp = 0L,
                sleepEndTimestamp = 1L
            ),
            SleepEntity(
                sleepStartTimestamp = 23L,
                sleepEndTimestamp = 32L
            )
        )

        sleepList.forEach { entity -> sleepManager.saveSleepData(entity) }
            .also { sleepManager.deleteAllSleepData() }

        when (val actual = sleepManager.readSleepData()) {
            is Result.Success -> assertThat(actual.data).isEqualTo(emptyList())
            is Result.Error -> throw Exception(actual.error.toString())
        }

    }

}