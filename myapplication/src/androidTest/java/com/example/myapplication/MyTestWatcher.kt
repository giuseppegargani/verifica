package com.example.myapplication

import android.util.Log
import org.junit.rules.TestWatcher
import org.junit.runner.Description

open class MyTestWatcher() : TestWatcher() {

    //var successi: Int = 0
    //var fallimenti: Int = 0

    override fun starting(description: Description?) {
        super.starting(description)
        Log.d("giuseppeRisultati", "Test iniziato")
        println("iniziatO Test!!!!!!")
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        Log.d("giuseppeRisultati", "nuovo TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto successo")
        println("nuovo TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto successo")
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Log.d("giuseppeRisultati", "Finiti i tests!!!!")
        println("FINITI I TESTS!!! e successi")
    }

}