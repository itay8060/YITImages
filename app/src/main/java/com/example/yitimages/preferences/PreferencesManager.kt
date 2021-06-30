package com.example.yitimages.preferences

import android.content.Context
import android.content.SharedPreferences

open class PreferenceManager constructor(context: Context) : IPreferenceHelper {

    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun setLastSearch(query: String) {
        preferences[LAST_QUERY] = query
    }

    override fun getLastSearch(): String {
        return preferences[LAST_QUERY] ?: ""
    }

    override fun clearPrefs() {
        preferences.edit().clear().apply()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> {
                getString(key, defaultValue as? String) as T?
            }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    companion object {
        const val PREFS_NAME = "ImagesSharedPrefs"
        const val LAST_QUERY = "last_query"
    }
}