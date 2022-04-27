package com.example.verifica

import android.content.Context
import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testwatcherlibrary.LoggerExample
import com.example.testwatcherlibrary.TestWatcherExample
import com.example.testwatcherlibrary.TestWatcherFrame
import org.junit.*

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.notification.RunListener
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.lang.Exception
import org.junit.rules.TestRule

import org.junit.internal.AssumptionViolatedException
import org.junit.runners.model.Statement

/*  COSE CHE FA' LA LIBRERIA:
    1 - Basta creare una istanza della classe della libreria come regola e stampa un resoconto!!!! in Json  (con i risultati dei testi e possibile annotazioni varie!!!)
        Di default vengono considerati tutti i tests!!!
*/

/* STEPS:
    Libreria locale di test strumentale!!! con resoconto e poi remota e poi vediamo se la carica da Github actions
    1 - Classe TestWatcher che scrive un file json or Xml (con i risultati)!!!!
    2 - Libreria locale
    3 - libreria esterna remota
*/

/* MA SI DEVE CANCELLARE IL MODULO PRINCIPALE OPPURE NO?
*/

/* FASE 1: CREARE FILE JSON
    - Dovrebbe avere un resoconto che riguardi successo, fallimento, saltati (nome progetto, nome classe, nomi tests)
    - Modifica se il file esiste già (vari rami) oppure lo crea di nuovo sulla base dei risultati della classe
    - Per test singolo (si possono anche impostare come oggetti della classe) il cambiamento per i test singoli, ma test per classe
*/

/* INFORMAZIONI
    - Baeldung (ca. 2017) : https://www.baeldung.com/junit-testwatcher
*/

/* Appunti vari:
    Rule implementa una istanza ogni volta che viene lanciato un test e questo test deve andare a modificare una variabile locale
    Ma si puo' anche creare una classe
    Vogliamo una classe che implementi una interfaccia e compia dei risultati!!
    TestImplementation!!!
*/


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: TestWatcherFrame() {
    var watchedLog: String? = null

    //sono due VARIABILI CUSTOM (il nome si puo' cambiare)
    private val testResultsStatus: List<TestResultStatus> = ArrayList()
    private enum class TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.verifica", appContext.packageName)
    }*/
    //si crea un oggetto TestWatcher per compiere delle azioni prima e dopo i tests!!!!   LA LIBRERIA PRIMA ERA LA CLASSE MYTESTWATCHER
    /*@get:Rule
    public val watchman: TestRule? = TestWatcherExample()*/
    /*public val watchman: TestRule? = object : MyTestWatcher() {} : TestWatcher() {

        override fun apply(base: Statement?, description: Description?): Statement? {
            return super.apply(base, description)
        }

        override fun succeeded(description: Description) {
            watchedLog += """${description.displayName} success!"""
            Log.d("giuseppeRisultati", "TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto successo")
            println("TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto successo")
        }

        override fun failed(e: Throwable, description: Description) {
            watchedLog += """${description.displayName} ${e.javaClass.simpleName}
"""
        }

        /*override fun skipped(e: AssumptionViolatedException, description: Description) {
            watchedLog += """${description.displayName} ${e.getClass().getSimpleName()}
"""
        }*/

        override fun starting(description: Description) {
            super.starting(description)
        }

        override fun finished(description: Description) {
            super.finished(description)
        }
    }*/

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /*companion object {
        @ClassRule
        @JvmField
        public val watchman: TestRule? = MyTestWatcher()
    }*/
    /*@get:ClassRule
    public val watchman: TestRule? = MyTestWatcher()*/

    /*@AfterClass
    open fun after_Class() {
        Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!!")
        println("terminata la classe!!!!!!")
    }*/

    @Test
    fun A_useAppContext() {

        LoggerExample.logMessage("giuseppeProva", "messaggio strumentale da libreria custom")
        //nameTest= "useAppContext"
        try {
            // Context of the app under test.
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            assertEquals("com.example.verifica", appContext.packageName)

            //una volta verificato si dovrebbe poter scrivere
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "giuseppe.txt")
            val stream = FileOutputStream(file)
            stream.use { stream ->
                stream.write("Nome del test: A_useAppContextTest successo".toByteArray())
            }
            //scriviConsole("A_useAppContextTest")
            //Log.d("giuseppe", "Test $nameTest riuscito!!!")
        }
        catch (exception: Exception){
            //Log.d("giuseppe", "Test $nameTest non riuscito!!! e ha generato una eccezione")
        }
    }

    @Test
    fun C_useAppContext(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.verifica", appContext.packageName)
    }

    @Test
    fun B_verificaLettura(){
        val context= InstrumentationRegistry.getInstrumentation().targetContext
        val path: File = context.getExternalFilesDir(null)!!
        val file = File(path, "giuseppe.txt")

        val reader = FileReader(file)
        val testo = reader.readText()
        //Log.d("giuseppeLettura", "****** stringa letta $txt")
        reader.close()
        //Log.d("giuseppeLettura", "letto dentro la funzione $letto")
        assertEquals("Nome del test: A_useAppContextTest successo", testo)
    }
}

//Si deve creare una classe che estende RunListener
class TestListener: RunListener() {

    //questo metodo quando vengono iniziati i test (si vede meglio in debug)
    override fun testRunStarted(description: Description?) {
        super.testRunStarted(description)
        Log.d("giuseppeRisultati", "Test iniziati dentro custom Listener")
        println("iniziati Test!!!!!!")
    }

    override fun testFinished(description: Description?) {
        super.testFinished(description)
        Log.d("giuseppeRisultati", "Test completato ${description?.displayName} e risultato: ")
    }

    /*override fun testSuiteFinished(description: Description?) {
        super.testSuiteFinished(description)
        Log.d("giuseppeRisultati", "Suite completata")
    }*/
}

//Si e' aggiunta come libreria locale!!!!!
/*e questo e' una classe che estende TestWatcher e scrive un file Json!!!! CLASSE OPEN!!!!!!
    ad ogni test si deve modifica un oggetto (aggiungendo o sottraendo degli elementi)
    come si fà per ereditarietà multiple?
 */
/*open class MyTestWatcher() : TestWatcher() {

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

}*/
