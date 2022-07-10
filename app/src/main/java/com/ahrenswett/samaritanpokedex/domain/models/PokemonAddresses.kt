package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val pokemonformat = Json { ignoreUnknownKeys = true }

@Serializable
data class PokemonAddresses(
    val name: String,
    val url: String
)

fun decodePokemon(responseBody: String): PokemonAddresses{
    return pokemonformat.decodeFromString(PokemonAddresses.serializer(), responseBody)
}
