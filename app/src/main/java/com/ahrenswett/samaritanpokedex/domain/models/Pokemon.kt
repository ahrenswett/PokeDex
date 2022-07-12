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
    val name : String?,
    val types : List<Type>,
    val stats: List<Stat>,
    val order : Int?,
    // getting this is killing me have asked in Serialization chat waiting for an answer
    val sprites: Other?,
    val weight: Int,
    val height: Int
)

@Serializable
data class Type(
   val type: Map<String,String>
)

@Serializable
data class Stat(
    val base_stat: Int,
    val effort : Int,
    val stat: Map<String,String>
)

@Serializable
data class Other(

    @SerialName("official-artwork") val officialArtwork: Map<String,String>?,
//    val other: Map<String,Map<String,String>>?
)



/*
 * Display this originally
- Picture: response.sprites.other.official-artwork.front_default
- Name: response.name
- Pokédex Number: response.order
- Type(s): response.type
 *
 */

/*on details page display this
- Top:
- Picture: response.sprites.other.official-artwork.front_default
- Name: response.name
- Pokédex Number: response.order
- About:
- Weight: response.weight / 10
- Height: response.weight / 10
- Type(s): response.types
- Base Stats:
- response.stats array
- Capture:
- Button if not captured
- Info if capture (localStorage.currentPokemon ≠ nil)
- Nickname: localStorage.currentPokemon.nickname (optional hide if none)
- Captured on: localStorage.currentPokemon.capturedDate
- Captured Level: localStorage.currentPokemon.capturedLevel
*/
// need a special second decoder to get  the above Information
fun decodePokemon(responseBody: String):Pokemon{
    return pokemonFormat.decodeFromString(Pokemon.serializer(), responseBody)
}