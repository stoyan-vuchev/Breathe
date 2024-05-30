package choehaualen.breath.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isNullOrEmpty
import choehaualen.breath.data.local.dao.SleepDao
import choehaualen.breath.data.local.entity.SleepEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.hours

@RunWith(AndroidJUnit4::class)
class AppDatabaseSleepTest {

    private lateinit var db: AppDatabase
    private lateinit var sleepDao: SleepDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.createInstance(context.applicationContext, true)
        sleepDao = db.sleepDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_and_read_sleep_entity() = runTest {

        val sleepStartTimestamp = System.currentTimeMillis()
        val sleepEndTimestamp = sleepStartTimestamp.plus(8.hours.inWholeMilliseconds)
        val totalSleepDuration = sleepEndTimestamp.minus(sleepStartTimestamp)

        val expected = SleepEntity(
            id = 1,
            sleepStartTimestamp = sleepStartTimestamp,
            sleepEndTimestamp = sleepEndTimestamp,
            totalSleepDuration = totalSleepDuration
        )

        sleepDao.insertSleepEntities(listOf(expected))

        val actual = sleepDao.getSleepEntity(expected.sleepStartTimestamp)

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun insert_and_read_sleep_entity_list() = runTest {

        val sleepStartTimestamp = System.currentTimeMillis()
        val sleepEndTimestamp = sleepStartTimestamp.plus(8.hours.inWholeMilliseconds)
        val totalSleepDuration = sleepEndTimestamp.minus(sleepStartTimestamp)

        val expected = listOf(
            SleepEntity(
                id = 1,
                sleepStartTimestamp = sleepStartTimestamp,
                sleepEndTimestamp = sleepEndTimestamp,
                totalSleepDuration = totalSleepDuration
            )
        )

        sleepDao.insertSleepEntities(expected)

        val actual = sleepDao.getAllSleepEntities()

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun insert_and_delete_sleep_entity() = runTest {

        val sleepStartTimestamp = System.currentTimeMillis()
        val sleepEndTimestamp = sleepStartTimestamp.plus(8.hours.inWholeMilliseconds)
        val totalSleepDuration = sleepEndTimestamp.minus(sleepStartTimestamp)

        val expected = SleepEntity(
            id = 1,
            sleepStartTimestamp = sleepStartTimestamp,
            sleepEndTimestamp = sleepEndTimestamp,
            totalSleepDuration = totalSleepDuration
        )

        sleepDao.insertSleepEntities(listOf(expected))
        sleepDao.deleteSleepEntity(expected)

        val actual = sleepDao.getSleepEntity(expected.sleepStartTimestamp)

        assertThat(actual).isNull()

    }

    @Test
    fun insert_and_delete_all_sleep_entities() = runTest {

        val sleepStartTimestamp = System.currentTimeMillis()
        val sleepEndTimestamp = sleepStartTimestamp.plus(8.hours.inWholeMilliseconds)
        val totalSleepDuration = sleepEndTimestamp.minus(sleepStartTimestamp)

        val expected = SleepEntity(
            id = 1,
            sleepStartTimestamp = sleepStartTimestamp,
            sleepEndTimestamp = sleepEndTimestamp,
            totalSleepDuration = totalSleepDuration
        )

        sleepDao.insertSleepEntities(listOf(expected))
        sleepDao.deleteSleepEntity(expected)

        val actual = sleepDao.getAllSleepEntities()

        assertThat(actual).isNullOrEmpty()

    }

}