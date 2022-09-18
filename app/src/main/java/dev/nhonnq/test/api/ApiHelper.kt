package dev.nhonnq.test.api

import dev.nhonnq.test.data.db.entity.Upcoming
import retrofit2.Response

interface ApiHelper {
    suspend fun fetchUpcoming(): Response<List<Upcoming>>
}