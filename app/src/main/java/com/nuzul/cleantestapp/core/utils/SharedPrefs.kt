package com.nuzul.cleantestapp.core.utils

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefs(private val context: Context) {
    companion object {
        private const val PREFS = "MyAppPrefs"
        private const val PREFS_TOKEN = "token"
    }

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveToken(token: String){
        put(PREFS_TOKEN, token)
    }

    fun getToken() : String {
        return get(PREFS_TOKEN, String::class.java)
    }

    fun clear() {
        sharedPrefs.edit().run {
            remove(PREFS_TOKEN)
        }.apply()
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPrefs.getString(key, "")
            Boolean::class.java -> sharedPrefs.getBoolean(key, false)
            Float::class.java -> sharedPrefs.getFloat(key, -1f)
            Double::class.java -> sharedPrefs.getFloat(key, -1f)
            Int::class.java -> sharedPrefs.getInt(key, -1)
            Long::class.java -> sharedPrefs.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPrefs.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }
}