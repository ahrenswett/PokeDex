package com.ahrenswett.samaritanpokedex.data

import com.ahrenswett.samaritanpokedex.modules.Pokemon
import kotlinx.coroutines.flow.Flow

interface NetworkPokemonDAO {
    fun getPokemonListFromNetwork(url: String): Flow<List<Pokemon>>
}