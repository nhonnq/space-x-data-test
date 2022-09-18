package dev.nhonnq.test

import android.app.Activity
import android.content.Intent
import dev.nhonnq.test.ui.main.MainActivity

/**
 * Open /ui/main/MainActivity
 */
fun Activity.openMainActivity() {
    val mIntent = Intent(this, MainActivity::class.java)
    this.startActivity(mIntent)
    this.finish()
}
