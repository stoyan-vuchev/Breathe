package io.proxima.breathe.presentation.main.home.mlassist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert
    suspend fun insertMessage(message: ChatMessage)

    @Query("SELECT * FROM chat_messages ORDER BY id DESC")
    fun getAllMessages(): Flow<List<ChatMessage>>
}
