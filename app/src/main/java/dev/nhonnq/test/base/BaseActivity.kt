package dev.nhonnq.test.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun observeViewModel()
    abstract fun showError(errorCode: Int)
    abstract fun showLoadingView()
    abstract fun hideLoadingView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        supportActionBar?.hide()
    }

}
