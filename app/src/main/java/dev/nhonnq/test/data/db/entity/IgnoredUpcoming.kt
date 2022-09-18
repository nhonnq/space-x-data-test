package dev.nhonnq.test.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IgnoredUpcoming")
data class IgnoredUpcoming(

    @PrimaryKey
    @ColumnInfo(name = "upcoming_id")
    var upcomingId: String,

    @ColumnInfo(name = "state")
    var state: String = "deleted",

)