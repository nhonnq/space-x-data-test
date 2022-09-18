package dev.nhonnq.test.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dev.nhonnq.test.Constants
import dev.nhonnq.test.base.BaseActivity
import dev.nhonnq.test.openMainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun showError(errorCode: Int) {}
    override fun showLoadingView() {}
    override fun hideLoadingView() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            delay(Constants.SPLASH_DELAY)
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        openMainActivity()
    }

}