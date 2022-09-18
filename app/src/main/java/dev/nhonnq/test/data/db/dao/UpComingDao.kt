package dev.nhonnq.test.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.nhonnq.test.data.db.entity.IgnoredUpcoming

@Dao
abstract class UpComingDao {
    @Query("DELETE FROM `IgnoredUpcoming`")
    abstract fun deleteIgnoredUpcoming()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun save(ignoredUpcoming: IgnoredUpcoming)

    @get:Query("SELECT * FROM `IgnoredUpcoming`")
    abstract val ignoredUpcomingLive: LiveData<List<IgnoredUpcoming>>

    @get:Query("SELECT * FROM `IgnoredUpcoming`")
    abstract val getIgnoredUpcomingList: List<IgnoredUpcoming>

    @Query("SELECT * FROM `IgnoredUpcoming` WHERE upcoming_id=:uuid")
    abstract fun ignoredUpcoming(uuid: String): IgnoredUpcoming
}