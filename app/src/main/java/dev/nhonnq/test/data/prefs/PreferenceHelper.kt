package dev.nhonnq.test.data.prefs

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import dev.nhonnq.test.MVVMApplication

object PreferenceHelper {

    private const val NAME_PREF = "NAME_PREF"
    private const val USER_DATA = "USER_DATA"

    private var instance: PreferenceHelper? = null

    fun getInstance(): PreferenceHelper {
        if (instance == null) {
            instance = PreferenceHelper
        }

        return instance as PreferenceHelper
    }
    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
    }

    private fun getLong(key: String): Long {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getLong(key, 0)
    }

    private fun getLong(key: String, defaultLong: Long): Long {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getLong(key, defaultLong)
    }

    private fun setLong(key: String, value: Long) {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        val editor = settings.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun getInt(key: String): Int {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getInt(key, 0)
    }

    private fun getInt(key: String, defaultInt: Int): Int {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getInt(key, defaultInt)
    }

    private fun setInt(key: String, value: Int) {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        val editor = settings.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getString(key, "") ?: ""
    }

    private fun getString(key: String, defaultString: String): String? {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getString(key, defaultString)
    }

    private fun setString(key: String, value: String) {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        val editor = settings.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getBoolean(key, false)
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        return settings.getBoolean(key, defaultValue)
    }

    private fun setBoolean(key: String, value: Boolean) {
        val settings: SharedPreferences = MVVMApplication.instance.getSharedPreferences(
            USER_DATA,
            Activity.MODE_PRIVATE
        )
        val editor = settings.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clear(context: Context) {
        getPreference(context).edit().clear().apply()
        context.getSharedPreferences(USER_DATA, Activity.MODE_PRIVATE).edit().clear().apply()
    }



}