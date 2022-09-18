package dev.nhonnq.test.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.nhonnq.test.data.db.dao.UpComingDao
import dev.nhonnq.test.data.db.entity.IgnoredUpcoming

@Database(entities = [IgnoredUpcoming::class], version = 1, exportSchema = false)
abstract class AppRoomDB : RoomDatabase() {

    abstract fun upcomingDao(): UpComingDao?

}