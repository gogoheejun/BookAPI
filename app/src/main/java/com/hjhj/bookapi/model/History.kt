package com.hjhj.bookapi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //entiy라고 해줌으로써 이 데이터클래스 자체가 하나의 테이블이 된다고 알려줌
data class History (
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "keyword") val keyword: String?
    )
