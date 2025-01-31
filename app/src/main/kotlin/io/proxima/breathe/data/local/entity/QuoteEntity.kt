package io.proxima.breathe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class QuoteEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Long,

    val author: String,
    val quote: String

) {

    companion object {

        val Default = QuoteEntity(
            id = System.currentTimeMillis(),
            author = "Maya Angelov",
            quote = "You will face many defeats in life, but never let yourself be defeated."
        )

    }

}