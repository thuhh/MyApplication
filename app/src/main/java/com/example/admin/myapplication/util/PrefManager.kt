package com.example.admin.myapplication.util

import android.content.Context
import android.content.SharedPreferences


class PrefManager(_context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    // shared pref mode
    private var PRIVATE_MODE = 0

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {

        // Shared preferences file name
        private val PREF_NAME = "MyShared"

        private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }

}