package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

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
    val Stats: List<Stat>?,
    val order : Int?,

    //not sure how to use Kotlin Serialization to get this
    val sprites: Map<String, JsonElement>

//    val nickname: String?,
//    val capturedDate: String?,
//    val capturedLevel: Int?,
//    val detailData: String?
)
//{
//    "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
//    "back_female": null,
//    "back_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png",
//    "back_shiny_female": null,
//    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
//    "front_female": null,
//    "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png",
//    "front_shiny_female": null,
//    "other": {
//    "dream_world": {
//        "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg",
//        "front_female": null
//    },
//    "home": {
//        "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png",
//        "front_female": null,
//        "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/1.png",
//        "front_shiny_female": null
//    },
//    "official-artwork": {
//        "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
//    }
//},


//@Serializable
//data class Types(
//    val types : List<Map<String,String>>
//)

@Serializable
data class Type(
   val type: Map<String,String>
)

//@Serializable
//data class Stats(
//    val stats: List<Map<String,String>>
//)
@Serializable
data class Stat(
    val stat: Map<String,String>
)

@Serializable
data class Other(
 val other: Map<String,String>
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