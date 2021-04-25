package com.hjhj.bookapi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hjhj.bookapi.dao.HistoryDao
import com.hjhj.bookapi.model.History

//데이터베이스라고 알려주고, 이 데이터베이스가 히스토리라는 테이블을 사용할거라고 알려줌...버전은 나중에 앱업데이트할때...
@Database(entities = [History::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}