package com.ahrenswett.samaritanpokedex.data

import com.ahrenswett.samaritanpokedex.data.api.NetworkSource
import com.ahrenswett.samaritanpokedex.domain.models.Response
import com.ahrenswett.samaritanpokedex.util.Constants
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@ActivityScoped
class Repository @Inject constructor(
    private val api: NetworkSource
){
    val baseResponse = runBlocking { getPokemonListResponse(Constants.BASE_URL) }

    suspend fun getPokemonListResponse(url:String): Response? {
//        TODO("Need to address passing the limit and offset")
        return api.loadPokeList(url)}


//   suspend fun network(){
//       val networkSource: NetworkSource = NetworkSource("https://pokeapi.co/api/v2/pokemon?limit=20&offset=0")
//   }

}

