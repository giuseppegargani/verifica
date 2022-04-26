package com.example.testwatcherlibrary

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast

//per crosscheck custom library
class ToasterExample {
    companion object {
        fun toastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
class LoggerExample {
    companion object {
        fun logMessage(tag: String, message: String) {
            Log.d(tag, message)
            println(message)
        }
    }
}