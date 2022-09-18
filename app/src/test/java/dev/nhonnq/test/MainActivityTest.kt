package dev.nhonnq.test

import androidx.lifecycle.Observer
import org.junit.Assert.*

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

import androidx.test.ext.junit.rules.ActivityScenarioRule
import dev.nhonnq.test.api.ApiService
import dev.nhonnq.test.data.Resource
import dev.nhonnq.test.data.db.entity.Upcoming
import dev.nhonnq.test.ui.main.MainActivity
import dev.nhonnq.test.ui.main.MainViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var apiUpcomingObserver: Observer<Resource<List<Upcoming>>?>

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        hiltAndroidRule.inject()

        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel()
        viewModel.upcoming.observeForever(apiUpcomingObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `when MainActivity launched and fetch upcoming data`() {
        activityScenarioRule.scenario
    }

    @Test
    fun `fetch upcoming from local file compare response Code 200 returned`() {
        // Fake response from file
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)

        runBlocking {
            // Actual Response
            val actualResponse = apiService.fetchUpcoming()
            assertEquals(
                response.toString().contains("200"),
                actualResponse.code().toString().contains("200"),
            )
        }
    }

    @Test
    fun `fetch upcoming from API and check valid data with important fields`() {
        runBlocking {
            val actualResponse = apiService.fetchUpcoming()
            val upcomingList: List<Upcoming>? = actualResponse.body()
            var checkValidData = true
            var checkIdsDuplicate = true
            upcomingList?.forEach {
                if (it.id.isEmpty())
                    checkValidData = false
                if (it.name.isNullOrEmpty())
                    checkValidData = false
                if (it.dateUtc.isNullOrEmpty())
                    checkValidData = false
            }
            val distinctList = upcomingList?.distinctBy { it.id }
            distinctList?.let {
                checkIdsDuplicate = upcomingList.isNotEmpty() && it.size == upcomingList.size
            }
            assertEquals(
                upcomingList?.isNotEmpty() == true && checkValidData && checkIdsDuplicate,
                true
            )
        }
    }

    @After
    fun tearDown() {
        viewModel.upcoming.removeObserver(apiUpcomingObserver)
        mockWebServer.shutdown()
    }

}

