package dev.nhonnq.test.listeners

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*

class DebouncingQueryTextListener(
lifecycle: Lifecycle,
private val onDebouncingQueryTextChange: (String?) -> Unit
) : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
    var debouncePeriod: Long = 200

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}