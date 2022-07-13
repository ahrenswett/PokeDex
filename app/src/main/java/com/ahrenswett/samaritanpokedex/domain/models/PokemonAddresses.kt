package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val pokemonAddressFormat = Json { ignoreUnknownKeys = true}

@Serializable
data class PokemonAddresses(
    val name: String,
    val url: String
)

fun decodePokemonAddress(responseBody: String): PokemonAddresses{
    return pokemonAddressFormat.decodeFromString(PokemonAddresses.serializer(), responseBody)
}
