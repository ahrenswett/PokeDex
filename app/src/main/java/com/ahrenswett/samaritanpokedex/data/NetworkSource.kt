package com.ahrenswett.samaritanpokedex.data

import com.ahrenswett.samaritanpokedex.modules.Response
import com.ahrenswett.samaritanpokedex.modules.decodeResponse
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// gets a specified URL and returns the results
// pass the response to the view model
class NetworkSource(url: String, private val client: HttpClient = PokeHttpClient){

    val response: Response? = runBlocking {loadPokeList(url)}

//  load the data and save it as the new response
    private suspend fun loadPokeList(url: String): Response? = withContext(Dispatchers.IO){
        val httpResponse = client.getPokemon(url)
        if (httpResponse != null) {
            return@withContext decodeResponse(httpResponse.bodyAsText())
        }
    return@withContext null
    }


}



suspend fun HttpClient.getPokemon(url: String): HttpResponse? {
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