package com.ahrenswett.samaritanpokedex.modules

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.logging.Level.parse

val pokemonformat = Json { ignoreUnknownKeys = true }

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

fun decodePokemon(responseBody: String): Pokemon{
    return pokemonformat.decodeFromString(Pokemon.serializer(), responseBody)
}
