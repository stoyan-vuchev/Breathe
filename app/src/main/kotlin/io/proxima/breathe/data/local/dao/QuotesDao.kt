package io.proxima.breathe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.proxima.breathe.data.local.entity.QuoteEntity

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quoteEntity: QuoteEntity)

    @Query("SELECT * FROM quotes_table ORDER BY id LIMIT 1")
    suspend fun getQuote(): QuoteEntity?

    @Query("DELETE FROM quotes_table")
    suspend fun deleteQuote()

}