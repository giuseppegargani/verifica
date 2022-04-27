package com.example.testwatcherlibrary

import android.util.Log
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/* RIFLESSIONI E LINKS!!!
    -

 */

/* NOTE:
    - Ha le sue dipendenze Junit4!!!! ma si puo' mettere anche con JUnit5
    - Si possono anche creare numerose e diverse classi!!! o proprietà!!!! Anzi generalmente una libreria e' composta di numerose classi!!!!!!!!!!!!!!!!!
    - l'ideale che una volta istanziata la classe non si debbano creare variabili aggiuntive!!! (ma si puo')
    - verifica che non esista gia' altrimenti modifica!!!
    - Creiamo un oggetto Json corrispondente ad un RAMO !!!! e poi verifichiamo se esiste o meno il file!!!
        il ramo corrispondente

    Si vuole un oggetto Json: { Instrumented: { ExampleInstrumented: { test1: Success }, },}

    - Si deve scrivere un oggetto Json!!!!!!!

    - SI DEVONO POTER INSERIRE DEI PARAMETRI (nome file, etc..) ed annotazioni!!!

    - Ma si possono caricare in Github Actions delle librerie custom? realizzate ed importate?? proviamo!!!
*/

/* DUBBI E DOMANDE:
    - Corretto creare la library in Implementation?
    - Corretto creato come interfaccia? (se ne possono prendere piu' di due? override dei suoi metodi?
    - Corretto creare variabile come companion ObJect? oppure singleton??
        https://proandroiddev.com/a-true-companion-exploring-kotlins-companion-objects-dbd864c0f7f5
        class property inside companion object: https://stackoverflow.com/questions/50642249/kotlin-how-to-use-a-class-property-in-a-companion-object
        https://medium.com/swlh/kotlin-basics-of-companion-objects-a8422c96779b
    - Ma come si impostano i settaggi per una interfaccia?? (credo nello stesso modo delle altre classi)
    - Si deve usare @BeforeClass? per inizializzare la prorietà di classe? (rivedere il funzionamento)
    - Si devono esporre delle proprietà nella libreria? per poter compiere delle cose con esse all'interno di instrumented Test?
 */

/* MIGLIORAMENTI E PARAMETRI!!! impostabili
    - XML o Json
    - Nome del file
    - tempi di esecuzione!!!!
    - (Run Listener e manifest)!!!!
    - JVMSTATIC e' inutile per Kotlin??
 */

/* JSON!!!! Ci sono diversi metodi:
    - java class JSONTokener : https://johncodeos.com/how-to-parse-json-in-android-using-kotlin/
        SENZA BISOGNO DI USARE SOFTWARE DI TERZE PARTI!!!!! Java
    - Gson : https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
                https://stackoverflow.com/questions/58100739/how-to-generate-a-json-object-in-kotlin
    - JsonObject: https://www.tutorialspoint.com/how-to-use-jsonobject-to-parse-json-in-android-using-kotlin
    - oppure Moshi :
 */
/* Esempio di Json: array di oggetti Json (che corrispondono ad un Data class (ma non possono avere elementi variabili?)) non tipizzati!!! (quindi senza dataClass)
    quindi variabili!!! ma si possono usare tutti e due i metodi in qualche modo!!! e a che scopo? per esempio si potrebbe riportare un numero o un dato...boh...
    l'oggetto inserito deve essere sempre uguale? ci possono essere diversi tipi di json restituiti.
    MODELLO BASE:  oggetto Json con array di classi con array di oggetti {name: risultato}    array di classi di test, array di metodi di test
    MODELLO UN POCHINO PIU' ELABORATO:
    MODELLO PIU' ELABORATO:
 */

//attualmente una classe che viene istanziata ad ogni singolo test!!! (e dovrebbe modificare una variabile esterna)
open class TestWatcherExample: TestWatcher() {

    //var successi: Int = 0
    //var fallimenti: Int = 0

    override fun starting(description: Description?) {
        super.starting(description)
        Log.d("giuseppeRisultati", "Test iniziato")
        println("iniziato il Test!!!!!!")
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        TestWatcherFrame.aggiungiSuccessi(TestWatcherFrame.TestResultStatus.SUCCESSFUL)
        Log.d("giuseppeRisultati", "nuovo TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto successo e nome ${description?.methodName}")
        println("nuovo TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto successo")
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        TestWatcherFrame.aggiungiSuccessi(TestWatcherFrame.TestResultStatus.FAILED)
        Log.d("giuseppeRisultati", "nuovo TESTWATCHER!!!!!!!!!!!!!!!! ${description} ha avuto fallimento e nome ${description?.methodName}")
        println("nuovo TESTWATCHER!!!!!!!!!!!!!!! ${description} ha avuto fallimento")
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Log.d("giuseppeRisultati", "Finito il test!!!!")
        println("FINITO IL TEST!!!")
    }
}