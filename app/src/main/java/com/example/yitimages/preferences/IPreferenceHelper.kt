package com.example.yitimages.preferences

interface IPreferenceHelper {
    fun setLastSearch(query: String)
    fun getLastSearch(): String
    fun clearPrefs()
}