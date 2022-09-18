package dev.nhonnq.test.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nhonnq.test.api.ApiHelper
import dev.nhonnq.test.base.BaseViewModel
import dev.nhonnq.test.data.Resource
import dev.nhonnq.test.data.db.entity.Upcoming
import dev.nhonnq.test.data.repositories.UpcomingRepository
import dev.nhonnq.test.errors.LOAD_UPCOMING_ERROR
import dev.nhonnq.test.errors.NO_INTERNET_CONNECTION
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var apiHelper: ApiHelper

    @Inject
    lateinit var upcomingRepository: UpcomingRepository

    /**
     * Original Data fetch via APIs
     */
    private val _upcoming = MutableLiveData<Resource<List<Upcoming>>>()
    val upcoming: LiveData<Resource<List<Upcoming>>>
        get() = _upcoming

    /**
     * Live Data when users search
     */
    private val _upcomingSearch = MutableLiveData<List<Upcoming>>()
    val upcomingSearch: LiveData<List<Upcoming>>
        get() = _upcomingSearch
    
    private var jobs: ArrayList<Job> = ArrayList()

    /**
     * Fetching data from API
     */
    fun fetchUpcomingData() {
        val job = viewModelScope.launch {
            _upcoming.postValue(Resource.Loading())
            if (isNetworkAvailable()) {
                upcomingRepository.getUpcoming().let {
                    if (it.isSuccessful) {
                        it.body().let { data ->
                            _upcoming.postValue(Resource.Success(filterData(data)))
                        }
                    } else _upcoming.postValue(Resource.Error(LOAD_UPCOMING_ERROR))
                }
            } else _upcoming.postValue(Resource.Error(NO_INTERNET_CONNECTION))
        }
        jobs.add(job)
    }

    /**
     * Apply logic to filter data not yet deleted/ignored
     */
    private fun filterData(data: List<Upcoming>?): List<Upcoming> {
        if (data.isNullOrEmpty()) return emptyList()
        upcomingRepository.getIgnoreUpcomingList().let { _localData ->
            val ids = _localData?.map { it.upcomingId }
            return data.filter { ids?.contains(it.id) == false && it.upcoming == true }.toList()
        }
    }

    /**
     * Action to ignore upcoming
     */
    fun ignoreUpcoming(uuid: String) {
        val job = viewModelScope.launch {
            upcomingRepository.saveIgnoreUpcoming(uuid, "ignored")
        }
        jobs.add(job)
    }

    /**
     * Action to delete upcoming
     */
    fun deleteUpcoming(uuid: String) {
        val job = viewModelScope.launch {
            upcomingRepository.saveIgnoreUpcoming(uuid, "deleted")
        }
        jobs.add(job)
    }

    /**
     * Search data by upcoming's name
     */
    fun search(query: String) {
        val searchResult: List<Upcoming>? = upcoming.value?.data?.filter {
            it.name!!.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }?.toList()
        _upcomingSearch.postValue(searchResult)
    }

    /**
     * Destroy jobs before Activity/Fragment is destroyed
     */
    fun destroyJobs() {
        jobs.forEach {
            if (!it.isCompleted && it.isActive)
                it.cancel()
        }
    }

}