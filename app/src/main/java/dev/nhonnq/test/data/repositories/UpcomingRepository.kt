package dev.nhonnq.test.data.repositories

import dev.nhonnq.test.api.ApiHelper
import dev.nhonnq.test.data.db.AppRoomDB
import dev.nhonnq.test.data.db.entity.IgnoredUpcoming
import javax.inject.Inject

class UpcomingRepository @Inject constructor(private val apiHelper: ApiHelper, private val appRoomDB: AppRoomDB) {

    suspend fun getUpcoming() =  apiHelper.fetchUpcoming()

    fun saveIgnoreUpcoming(uuid: String, state: String) = appRoomDB.upcomingDao()?.save(IgnoredUpcoming(upcomingId = uuid, state = state))

    fun getIgnoreUpcomingList() = appRoomDB.upcomingDao()?.getIgnoredUpcomingList

}