package com.example.greenmind.data.local

import android.content.Context

object GreenMindDatabaseProvider {

    lateinit var dbLocal: GreenMindDatabase
        private set

    fun createDatabase(context: Context) {
        dbLocal = GreenMindDatabase.getInstance(context)
    }
}