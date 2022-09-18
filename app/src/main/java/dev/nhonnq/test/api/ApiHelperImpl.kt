package dev.nhonnq.test.api

import dev.nhonnq.test.data.db.entity.Upcoming
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun fetchUpcoming(): Response<List<Upcoming>> = apiService.fetchUpcoming()

}