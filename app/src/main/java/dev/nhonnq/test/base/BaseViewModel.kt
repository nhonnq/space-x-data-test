package dev.nhonnq.test.base

import androidx.lifecycle.ViewModel
import dev.nhonnq.test.errors.ErrorManager
import dev.nhonnq.test.utils.NetworkConnectivity
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    /**Inject Singleton ErrorManager
     * Use this errorManager to get the Errors
     */
    @Inject
    lateinit var errorManager: ErrorManager

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    fun isNetworkAvailable(): Boolean {
        return networkConnectivity.isConnected()
    }

}
