package com.example.mvvm.networking

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PokemonAPI {
    val BASE_URL = "https://pokeapi.co/api/v2/pokemon/"

    val TAG = "PokemonAPI"

    fun getPokemon(pokemonName: String) {
        Thread {
            val connection = URL("$BASE_URL$pokemonName").openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            try {
                val reader = InputStreamReader(connection.inputStream)

                reader.use { input ->
                    val response = StringBuilder()
                    val bufferedReader = BufferedReader(input)

                    bufferedReader.forEachLine {
                        response.append(it.trim())
                    }

                    Log.d(TAG, "In_Success $response")
                }
            } catch (e: Exception) {
                Log.d(TAG, "In_Error ${e.localizedMessage}")
            }

            connection.disconnect()

        }.start()
    }
}