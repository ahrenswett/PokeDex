package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val pokemonFormat = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    explicitNulls = false
    isLenient = true }

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int,
    val height: Int,
)

@Serializable
data class Sprites(
    val back_default: String,
    val back_female: String?,
    val back_shiny: String,
    val back_shiny_female: String?,
    val front_default: String,
    val front_female: String?,
    val front_shiny: String,
    val front_shiny_female: String?,
    val other: Other,
)

@Serializable
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatX
)

@Serializable
data class Type(
    val slot: Int,
    val type: TypeX
)

@Serializable
data class Other(
    val dream_world: DreamWorld,
    val home: Home,
    @SerialName("official-artwork")val officialArtwork: OfficialArtwork
)

@Serializable
data class DreamWorld(
    val front_default: String,
    val front_female: String?
)
@Serializable
data class Home(
    val front_default: String,
    val front_female: String?,
    val front_shiny: String,
    val front_shiny_female: String?
)

@Serializable
data class OfficialArtwork(
    val front_default: String
)

@Serializable
data class StatX(
    val name: String,
    val url: String
)
@Serializable
data class TypeX(
    val name: String,
    val url: String
)

fun decodePokemon(responseBody: String):Pokemon{
    return pokemonFormat.decodeFromString(Pokemon.serializer(), responseBody)
}