package com.example.verifica

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.verifica", appContext.packageName)
    }*/

    @Test
    fun A_useAppContext() {
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
                stream.write("A_useAppContextTest successo".toByteArray())
            }
            //scriviConsole("A_useAppContextTest")
            //Log.d("giuseppe", "Test $nameTest riuscito!!!")
        }
        catch (exception: Exception){
            //Log.d("giuseppe", "Test $nameTest non riuscito!!! e ha generato una eccezione")
        }
    }
}
