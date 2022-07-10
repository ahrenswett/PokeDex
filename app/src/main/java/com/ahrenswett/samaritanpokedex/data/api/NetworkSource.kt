package com.ahrenswett.samaritanpokedex.data.api

import com.ahrenswett.samaritanpokedex.data.PokeHttpClient
import com.ahrenswett.samaritanpokedex.domain.models.Response
import com.ahrenswett.samaritanpokedex.domain.models.decodeResponse
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// gets a specified URL and returns the results
// pass the response to the view model
class NetworkSource(private val client: HttpClient = PokeHttpClient){

//  load the data and save it as the new response
    suspend fun loadPokeList(url: String): Response? = withContext(Dispatchers.IO){
        val httpResponse = client.getPokemonAddresses(url)
        if (httpResponse != null) {
            return@withContext decodeResponse(httpResponse.bodyAsText())
        }
    return@withContext null
    }
}

suspend fun HttpClient.getPokemonAddresses(url: String): HttpResponse? {
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