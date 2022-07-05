package com.kareemdev.mycocktailsdb.utils

import android.content.ContentValues
import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object DoesNetworkHaveInternet {

    fun execute(): Boolean {
        return try {
            Log.d(ContentValues.TAG, "PING GOOGLE")
            val socket = Socket()
            socket.connect(
                InetSocketAddress("8.8.8.8", 53),
                1500
            )
            socket.close()
            Log.d(ContentValues.TAG, "PING Success.")
            true
        } catch (e: IOException) {
            Log.e(ContentValues.TAG, "NO internet connection. ")
            false
        }
    }
}