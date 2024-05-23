package choehaualen.breath.data.local

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import choehaualen.breath.data.local.entity.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var database: UserDatabase
    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        database = UserDatabase.createInstance(
            applicationContext = ApplicationProvider.getApplicationContext(),
            inMemory = true
        ).also { db -> dao = db.dao }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndGetUser() = runTest {

        val entity = UserEntity(name = "Alen")
        val id = dao.insertUser(entity)

        val actual = dao.getUserById(id)
        val expected = entity.copy(id = id)

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun insertAndDeleteUser() = runTest {

        val entity = UserEntity(name = "Jack")
        val id = dao.insertUser(entity)

        dao.deleteUser(entity.copy(id = id))

        val actual = dao.getUserById(id)
        assertThat(actual).isNull()

    }

    @Test
    fun insertAndUpdateUser() = runTest {

        val entity = UserEntity(name = "Stoyan")
        val newEntity = UserEntity(name = "Tony")

        val id = dao.insertUser(entity)
        dao.updateUser(newEntity.copy(id = id))

        val actual = dao.getUserById(id)
        val expected = newEntity.copy(id = id)

        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun flushDatabase() = runTest {

        val entity = UserEntity(name = "Mike")
        val id = dao.insertUser(entity)

        dao.flushDatabase()
        val actual = dao.getUserById(id)

        assertThat(actual).isNull()

    }

}