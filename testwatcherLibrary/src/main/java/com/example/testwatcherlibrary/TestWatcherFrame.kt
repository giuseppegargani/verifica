package com.example.testwatcherlibrary

import android.net.Uri
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.junit.AfterClass
import org.junit.Rule
import org.junit.rules.TestRule
import java.io.*
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.FileChannel.open

/* Si dovrebbe azzerare il conteggio oppure non c'è bisogno?
*/

/* TESTWATCHER DENTRO CLASSE (invece che regola)  e DEVE ESSERE STATICA!!!!
    https://stackoverflow.com/questions/9942825/how-to-tell-when-junit-finishes-by-just-using-a-testwatcher
    https://stackoverflow.com/questions/52113987/the-classrule-resources-must-be-static-kotlin
    BAELDUNG: https://www.baeldung.com/junit-testwatcher
 */

/* JSON:
    - INTERESSANTE: https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
    - https://stackoverflow.com/questions/58100739/how-to-generate-a-json-object-in-kotlin
    - https://stackoverflow.com/questions/62474129/create-write-edit-json-file-in-android-studio
 */

/* hacker videogames
    - https://www.moregameslike.com/nite-team-4/
 */

/* VERIFICARE SCRITTURA E LETTURA!!!!!! metodo migliore e blocco try-catch!!!!

 */

open class TestWatcherFrame {

    //con i possibili eventi dei tests
    enum class TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    companion object {

        // variabili che vengono modificate dai singoli tests!!
        var testResultsStatus: MutableList<TestResultStatus> = ArrayList()
        var nomePacchetto: String = ""
        var nomeClasseAttuale: String = ""

        //Si devono mettere come static!!!
        @AfterClass
        @JvmStatic
        fun afterClass() {
            Log.d("giuseppeRisultati", " nome Classe: ${javaClass.canonicalName}")
            Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!!")
            println("terminata la classe!!!!!! e successi $testResultsStatus")
            //funzione creata da Giuseppe alla conclusione
            createJson()
        }

        //metodo che aggiunge i successi a oggetto Json da salvare
        fun aggiungiSuccessi(risultato: TestResultStatus){
            testResultsStatus.add(risultato)
        }

        //funzione che crea il file Json
        fun createJson(){

            //verifica se esiste o meno
            val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")

            //val file = File(getApplicationContext<Context>().filesDir, "whatever.txt")

            //Verifica se esiste e modifica ramo
            if (file.exists()) {
                //si deve leggere ed assegnare a Json e leggere json
                Log.d("giuseppeJson", "Il file esiste ed e' ")
                var classeSingola=leggiJson(file)
                Log.d("giuseppeJson", " Oggetto json letto da file ${classeSingola?.listaTests?.get(0)?.nameTest}")

                //si deve aggiungere o modificare (verifica che sia presente un elemento della lista!!)
                //si crea un elemento inventato (singolo elemento)
                val singoloModifica = SingleTest("primo Test","Failure")
                val singoloAggiuuntivo = SingleTest("Terzo Test", "Success" )

                //SE ESATTAMENTE LO STESSO lascia invariato, altrimenti modifica, oppure se il nome diverso aggiungi
                //classeSingola?.listaTests?.map { if(it.nameTest == singoloModifica.nameTest) { it=singoloModifica } }
                //classeSingola= classeSingola?.listaTests?.map { if (it.nameTest==singoloModifica.nameTest) { it=singoloModifica} }
                classeSingola?.listaTests?.map { if(it.nameTest==singoloModifica.nameTest) { Log.d("giuseppeJson", "trovato un elemento che non si chiama come elemento aggiuntivo ma ${it.nameTest}");
                    it.outcome=singoloModifica.outcome; it.altriElementi=singoloModifica.altriElementi } }
                Log.d("giuseppeJson", "elemento modificato ${classeSingola?.listaTests?.get(0)}")

                // e aggiunge
                classeSingola?.listaTests?.add(singoloAggiuuntivo)
                Log.d("giuseppeJson", "Lista aggiornata ${classeSingola}")

            } else {
                //se non esiste crealo ex-novo
                //si prende il nome della classe e si scompone (si toglie il nome del modulo dalla classe)
            Log.d("giuseppeJson", "il file non esiste e va creato")

            }
            //se il file esiste modificalo altrimenti crealo di nuovo!!!

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
            var secondoElemento = SingleTest("secondo test", "Success")
            var classeAttuale = SingleClass(mutableListOf(elementoSingolo, secondoElemento))

            var jsonString = Gson().toJson(elementoSingolo)
            var jsonStringLista = Gson().toJson(classeAttuale)
            var testRipreso = Gson().fromJson<SingleClass>(jsonStringLista, SingleClass::class.java)
            Log.d("giuseppeJson", "elemento jsonString $jsonString e lista $classeAttuale")
            Log.d("giuseppeJson", "elemento json $classeAttuale")
            Log.d("giuseppeJson", " e rintracciare un elemento da nested ${testRipreso.listaTests[1].nameTest}")

            /*val context= InstrumentationRegistry.getInstrumentation().targetContext
            val path: File = context.getExternalFilesDir(null)!!
            Log.d("giuseppe", "nome directory $path")
            val file = File(path, "risultato.txt")*/
            val stream = FileOutputStream(file)
            stream.use { stream ->
                stream.write(jsonStringLista.toByteArray())
            }
        }

        //si recupera un oggetto Json da un file!!!
        fun leggiJson (file: File): SingleClass? {
            var txt: String? = null
            var singleClass:SingleClass? = null

            try {
                val reader = FileReader(file)
                txt = reader.readText()
                Log.d("giuseppeLettura", "stringa letta $txt")
                reader.close()
            } catch (e: IOException) {
                // Exception
                e.printStackTrace()
            }
            //se text diverso da null convertilo in oggetto Json (altrimenti restituira' null come singleClass)
            txt?.let {
                singleClass = Gson().fromJson<SingleClass>(it, SingleClass::class.java)
            }

            return singleClass
        }

        //da file o Uri
        /*fun ottieniLista(uri: Uri): MutableList<String> {

            var listaRighe: MutableList<String> = mutableListOf()

            //var fileDirectory =  getExternalFilesDir("Documents")
            //var externalFile = File(fileDirectory, "salvataggioPaziente.txt")
            //Log.d("giuseppeLettura", "percorso ${externalFile}")

            //val bufferedInputStream = BufferedInputStream( resources.openRawResource(R.raw.filename))
            val inputStream = getInputStreamByUri(context,uri)
            val bufferedInputStream = BufferedInputStream( inputStream  )

            //val bufferedReader = new BufferedReader(Paths.get("/resources/students.csv"));
            //val bufferedInputStream1 = BufferedInputStream( Paths.get(/resources/))
            val bufferedReader = BufferedReader(InputStreamReader(bufferedInputStream))
            try {
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    listaRighe.add(line!!)
                    //Log.d("giuseppeLettura", "valore linea ${line!!}\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return listaRighe
        }*/

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
    /*@Override
    fun afterAll(){
        Log.d("giuseppeRisultati", "terminata la classe!!!!!!!!!!! e $successi")
        println("terminata la classe!!!!!! e successi")
    }*/
}