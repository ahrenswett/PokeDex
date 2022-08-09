package com.ahrenswett.samaritanpokedex.util

import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import kotlinx.serialization.json.Json

data class DummyPoke (val bulbasaur: Pokemon = Json.decodeFromString(Pokemon.serializer(),Bulbasaur)){
}