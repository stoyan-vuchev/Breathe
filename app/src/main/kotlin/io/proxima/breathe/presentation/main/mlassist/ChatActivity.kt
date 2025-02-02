package io.proxima.breathe.presentation.main.mlassist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import io.proxima.breathe.presentation.main.home.mlassist.ChatDatabase
import io.proxima.breathe.presentation.main.home.mlassist.ChatMessage

//@Entity(tableName = "chat_messages")
//data class ChatMessage(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val message: String,

class ChatActivity : ComponentActivity() {
    private lateinit var db: ChatDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ChatDatabase.getDatabase(this)
        setContent { ChatScreen(db) }
    }
}

@Composable
fun ChatScreen(db: ChatDatabase? = null) {
    var userMessage by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }

    LaunchedEffect(Unit) {
        db?.chatDao()?.getAllMessages()?.collect { messages ->
            chatMessages.clear()
            chatMessages.addAll(messages)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(chatMessages) { message ->
                Text(text = if (message.isUser) "User: ${message.message}" else "Gemini: ${message.message}")
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(value = userMessage, onValueChange = { userMessage = it }, modifier = Modifier.weight(1f))
            Button(onClick = { /* Handle message send */ }) { Text("Send") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}




