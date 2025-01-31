package io.proxima.breathe.data.manager

import io.proxima.breathe.core.etc.Result
import io.proxima.breathe.core.etc.Result.Companion.extractErrorFromException
import io.proxima.breathe.data.local.dao.SleepDao
import io.proxima.breathe.data.local.entity.SleepEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SleepManager @Inject constructor(
    private val sleepDao: SleepDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun saveSleepData(sleepEntities: List<SleepEntity>): Result<Unit> = try {

        // Attempt to save the sleep data.
        withContext(ioDispatcher) { sleepDao.insertSleepEntities(sleepEntities) }

        // The data was saved successfully, now return a successful result.
        Result.Success()

    } catch (e: Exception) {

        // Notify the user of an occurred error.
        Result.Error(error = extractErrorFromException(e))

    }

    suspend fun readSleepData(): Result<List<SleepEntity>> = try {

        // Attempt to obtain the sleep data.
        val sleepData = withContext(ioDispatcher) {
            sleepDao.getAllSleepEntities() ?: listOf()
        }

        // Return the successfully obtained data.
        Result.Success(sleepData)

    } catch (e: Exception) {

        // Notify the user of an occurred error.
        Result.Error(error = extractErrorFromException(e))

    }

    fun readSleepDataFlow(): Flow<List<SleepEntity>> {

        // Attempt to obtain the sleep data.
        return sleepDao.getAllSleepEntitiesFlow() ?: flowOf()

    }

    suspend fun deleteAllSleepData(): Result<Unit> = try {

        // Attempt to delete all of the sleep data.
        withContext(ioDispatcher) { sleepDao.deleteAllSleepData() }

        // All of the data was deleted successfully, now return nothing. XD
        Result.Success()

    } catch (e: Exception) {

        // Notify the user of an occurred error.
        Result.Error(error = extractErrorFromException(e))

    }

}