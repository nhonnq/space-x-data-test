package dev.nhonnq.test

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MVVMApplication: MultiDexApplication() {

    companion object {
        lateinit var instance: MVVMApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}