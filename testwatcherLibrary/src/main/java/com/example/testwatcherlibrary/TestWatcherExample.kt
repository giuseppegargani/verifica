package com.example.testwatcherlibrary

import android.util.Log
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*NOTE:
    - Ha le sue dipendenze Junit4!!!! ma si puo' mettere anche con JUnit5
    - Si possono anche creare numerose e diverse classi!!! o proprietà!!!! Anzi generalmente una libreria e' composta di numerose classi!!!!!!!!!!!!!!!!!
    - l'ideale che una volta istanziata la classe non si debbano creare variabili aggiuntive!!! (ma si puo')
 */

//attualmente una classe che viene istanziata ad ogni singolo test!!! (e dovrebbe modificare una variabile esterna)
open class TestWatcherExample: TestWatcher() {

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