package com.example.testwatcherlibrary

import android.util.Log
import org.junit.Rule
import org.junit.rules.TestRule

open class TestWatcherFrame {

    init {
        Log.d("giuseppeRisultati", "inizializzato il frame")
        println("nuovo frame!!!!!!")
    }
    @get:Rule
    public val watchman: TestRule? = TestWatcherExample()
}