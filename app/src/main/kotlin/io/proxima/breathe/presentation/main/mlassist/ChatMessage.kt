package io.proxima.breathe.presentation.main.home.mlassist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val message: String,
    val isUser: Boolean
)
