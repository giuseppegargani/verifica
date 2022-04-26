package com.example.verifica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testwatcherlibrary.LoggerExample
import com.example.testwatcherlibrary.ToasterExample

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //per verificare che importi correttamente la libreria custom!!!!!!!
        ToasterExample.toastMessage(this, "ecco il toast iniziale")
        for(i in 0..5){
            LoggerExample.logMessage("giuseppeProva", "Ecco il primo messaggio custom library")
        }
    }
}