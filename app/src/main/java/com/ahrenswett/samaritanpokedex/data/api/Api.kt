package com.ahrenswett.samaritanpokedex.data.api

import com.ahrenswett.samaritanpokedex.data.PokeHttpClient
import com.ahrenswett.samaritanpokedex.domain.models.*
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// gets a specified URL and returns the results
// pass the response to the view model
class Api(private val client: HttpClient = PokeHttpClient){




//  load the data and save it as the new response
    suspend fun loadPokeAddresses(url: String): Response = withContext(Dispatchers.IO){
        return@withContext decodeResponse(client.getPoke(url)!!.bodyAsText())
    }

    suspend fun getListItem(url: String): Pokemon = withContext(Dispatchers.IO){
        println(url)
        var pokemon = decodePokemon(client.getPoke(url)!!.bodyAsText())
        pokemon.url = url
        return@withContext pokemon
    }

}

suspend fun HttpClient.getPoke(url: String): HttpResponse? {
    return try {
        get(url)
    } catch (e: Error) {
        UiEvent.ShowSnackBar(
            "Error: ${e.message}",
            "Make sure you have an Internet connection and retry"
        )
        null
    }
}