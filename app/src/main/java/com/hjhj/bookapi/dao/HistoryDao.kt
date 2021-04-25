package com.hjhj.bookapi.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hjhj.bookapi.model.History

@Dao //이렇게 하면 룸에 연결된다는 것임
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history WHERE keyword == :keyword")
    fun delete(keyword:String)
}