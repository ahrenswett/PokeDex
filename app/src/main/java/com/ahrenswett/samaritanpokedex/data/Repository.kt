package com.ahrenswett.samaritanpokedex.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahrenswett.samaritanpokedex.data.api.Api
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.domain.models.PokemonAddresses
import com.ahrenswett.samaritanpokedex.domain.models.Response
import com.ahrenswett.samaritanpokedex.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped


import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@ActivityScoped
class Repository @Inject constructor(
    private val api: Api
){

    var response: Response? = null

    suspend fun getPokemonListResponse(url:String): Response {
        return api.loadPokeAddresses(url)}

    //    would like to make this a flow returning a pokemon that is collected in a list that
    suspend fun getListItems(urls: List<PokemonAddresses>) : List<Pokemon>{
        val list: MutableList<Pokemon> = arrayListOf()
        urls.forEach { poke ->
            list.add (api.getListItem(poke.url))
        }
        return (list)
    }

    suspend fun getCapturedPokemon(url: String):Pokemon{
        return api.getListItem(url)
    }


}
