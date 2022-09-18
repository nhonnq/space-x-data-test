package dev.nhonnq.test.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import dev.nhonnq.test.R
import dev.nhonnq.test.base.BaseActivity
import dev.nhonnq.test.base.adapter.EndlessRecyclerAdapter
import dev.nhonnq.test.data.Resource
import dev.nhonnq.test.data.db.entity.Upcoming
import dev.nhonnq.test.databinding.ActivityMainBinding
import dev.nhonnq.test.extensions.*
import dev.nhonnq.test.listeners.DebouncingQueryTextListener
import dev.nhonnq.test.listeners.ItemSwipeListener
import dev.nhonnq.test.ui.dialog.DialogFactory
import dev.nhonnq.test.utils.SwipeTouchHelper
import dev.nhonnq.test.utils.observe
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener,
    EndlessRecyclerAdapter.RequestToLoadMoreListener {

    /**
     * ViewModel, Binding
     */
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    /**
     * Adapters
     */
    private var endlessAdapter: EndlessRecyclerAdapter? = null
    private var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding View
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        initSearchFlow()
    }

    /**
     * Setup RecyclerView to display data in the list
     * Apply PullToRefresh, loadMore
     * Apply Swipe to action callback
     */
    private fun setupRecyclerView(data: List<Upcoming>) {
        if (mainAdapter != null) {
            // Refresh Data
            mainAdapter?.setDataSource(data)
            return
        }
        mainAdapter = MainAdapter(this, layoutInflater, data.toArrayList())
        endlessAdapter = EndlessRecyclerAdapter(this, mainAdapter!!, this)
        val mLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = mLayoutManager
        val itemTouchHelper = ItemTouchHelper(SwipeTouchHelper(this, object : ItemSwipeListener {
            override fun onSwipeLeft(position: Int) {
                mainAdapter?.getDataSource()?.get(position)?.id?.let {
                    mainViewModel.deleteUpcoming(it)
                    mainAdapter?.removeAtPosition(position)
                }
            }

            override fun onSwipeRight(position: Int) {
                mainAdapter?.getDataSource()?.get(position)?.id?.let {
                    mainViewModel.ignoreUpcoming(it)
                    mainAdapter?.removeAtPosition(position)
                }
            }
        }))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = endlessAdapter
    }

    /**
     * Initialize search view and handle state of UIs
     */
    private fun initSearchFlow() {
        binding.searchView.onClick { binding.searchView.isIconified = false }
        binding.searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(
                this@MainActivity.lifecycle
            ) { newText ->
                newText?.let {
                    if (it.isEmpty()) {
                        mainViewModel.upcoming.value?.data?.let { list ->
                            setupRecyclerView(list)
                            hideEmptyData()
                        }
                        changeStatusRefreshLayout(true)
                    } else {
                        mainViewModel.search(it.trim())
                        changeStatusRefreshLayout(false)
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.fetchUpcomingData()
    }

    override fun onDestroy() {
        mainViewModel.destroyJobs()
        super.onDestroy()
    }

    override fun observeViewModel() {
        observe(mainViewModel.upcoming, ::handleDataResult)
        observe(mainViewModel.upcomingSearch, ::handleSearchResult)
    }

    /**
     * Mapping to show error message by error code
     */
    override fun showError(errorCode: Int) {
        val error = mainViewModel.errorManager.getError(errorCode)
        DialogFactory.showSimpleDialog(
            this,
            title = R.string.error_title.getString(),
            message = error.description
        )
    }

    override fun showLoadingView() {
        if (binding.swipeRefreshLayout.isRefreshing) return
        binding.progressBar.toVisible()
    }

    override fun hideLoadingView() {
        hideRefreshLayout()
        binding.progressBar.toGone()
    }

    private fun showEmptyData() {
        binding.txtNoData.toVisible()
    }

    private fun hideEmptyData() {
        binding.txtNoData.toGone()
    }

    /**
     * Handle data loading state / Show data on UIs
     */
    private fun handleDataResult(status: Resource<List<Upcoming>>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let {
                lifecycleScope.launch {
                    hideLoadingView()
                    if (status.data.isNullOrEmpty()) {
                        showEmptyData()
                        return@launch
                    } else hideEmptyData()
                    // Setup Views
                    setupRecyclerView(status.data)
                }
            }
            is Resource.Error -> {
                hideRefreshLayout()
                status.errorCode?.let { showError(it) }
            }
        }
    }

    /**
     * Search result handler UIs
     */
    private fun handleSearchResult(list: List<Upcoming>?) {
        if (list.isNullOrEmpty()) {
            setupRecyclerView(emptyList())
            showEmptyData()
        } else {
            setupRecyclerView(list)
            hideEmptyData()
        }
    }

    /**
     * Enable/Disable PullToRefresh while search
     */
    private fun changeStatusRefreshLayout(enable: Boolean) {
        binding.swipeRefreshLayout.isEnabled = enable
        if (!enable && binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun hideRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            if (isRefreshing) {
                isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            mainViewModel.fetchUpcomingData()
        }
    }

    override fun onLoadMoreRequested() {
        //TODO: Not yet implemented right now
    }

}