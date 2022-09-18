package dev.nhonnq.test.api

import dev.nhonnq.test.data.db.entity.Upcoming
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {

    companion object {
        private const val UPCOMING = "v4/launches/upcoming"
    }

    @GET(UPCOMING)
    suspend fun fetchUpcoming(): Response<List<Upcoming>>

}