package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val pokemonFormat = Json { ignoreUnknownKeys = true; coerceInputValues = true   }
@Serializable
data class Pokemon(
    val name : String?,
    val types : List<Type>,
//    val Stats: Stats?,
    val order : Int?,
//    val nickname: String?,
//    val capturedDate: String?,
//    val capturedLevel: Int?,
//    val detailData: String?
)


@Serializable
data class Types(
    val types : List<Map<String,String>>,

)

@Serializable
data class Type(
   val type: Map<String,String>
)


@Serializable
data class Stats(
    val stats: List<String>
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