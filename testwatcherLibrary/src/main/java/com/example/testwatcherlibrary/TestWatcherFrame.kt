package com.example.testwatcherlibrary

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.junit.After
import org.junit.AfterClass
import org.junit.Rule
import org.junit.rules.TestRule
import java.io.File
import java.io.FileOutputStream

/* Prima si azzera il conteggio e poi in fondo si vede il risultato!!
*/

open class TestWatcherFrame {

    //var successi: Int = 0

    enum class TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    //alcuni metodi sono statici ma si deve togliere JVMStatic!!! (da ricercare il valore)
    companion object {
        var testResultsStatus: MutableList<TestResultStatus> = ArrayList()
        var successi = 0

        //oggetto json da salvare
        var finaleJson: JsonObject? = null

        //Si devono mettere come static!!!
        @AfterClass
        @JvmStatic
        fun afterClass() {
            Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!!")
            println("terminata la classe!!!!!! e successi $testResultsStatus")
            //funzione creata da Giuseppe alla conclusione
            createJson()
        }

        fun aggiungiSuccessi(risultato: TestResultStatus){
            testResultsStatus.add(risultato)
        }

        //funzione che crea il file Json
        fun createJson(){


            //la lista nel nostro caso potrebbe essere una lista di elementi della Data class
            /*val list = listOf("String1", "String2")
            val gson = Gson()
            //da una stringa json ad un oggetto Json attraverso la serializzazione!!!!
            val jsonString = gson.toJson(list)
            val sType = object : TypeToken<List<String>>() { }.type
            val otherList = gson.fromJson<List<String>>(jsonString, sType)
            Log.d("giuseppeJson", "otherList $otherList")*/

            //un test singolo (con la data class)
            var elementoSingolo = SingleTest("primo Test", "Success")
            var secondoElemento = SingleTest("secondo test", "success")
            var classeAttuale = SingleClass(mutableListOf(elementoSingolo, secondoElemento))

            var jsonString = Gson().toJson(elementoSingolo)
            var jsonStringLista = Gson().toJson(classeAttuale)
            var testRipreso = Gson().fromJson<SingleClass>(jsonStringLista, SingleClass::class.java)
            Log.d("giuseppeJson", "elemento jsonString $jsonString e lista $classeAttuale")
            Log.d("giuseppeJson", "elemento json $classeAttuale")
            Log.d("giuseppeJson", " e rintracciare un elemento da nested ${testRipreso.listaTests[1].nameTest}")

            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")
            val stream = FileOutputStream(file)
            stream.use { stream ->
                stream.write(jsonStringLista.toByteArray())
            }
        }

    }

    init {
        Log.d("giuseppeRisultati", "inizializzato il frame")
        println("nuovo frame!!!!!!")
    }

    //regola per ogni test!!!! classe singola!!!! e nella stessa libreria!!!!
    @get:Rule
    public val watchman: TestRule? = TestWatcherExample()

    /*@After
    open static fun afterClass(){
        Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!!")
        println("terminata la classe!!!!!!")
    }*/
    @Override
    fun afterAll(){
        Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!! e $successi")
        println("terminata la classe!!!!!! e successi")
    }
}