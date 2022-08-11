package com.ahrenswett.samaritanpokedex.util

import com.ahrenswett.samaritanpokedex.domain.models.CapturedPokemon
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.random.Random.Default.nextInt

data class DummyPoke (val bulbasaur: Pokemon = Json.decodeFromString(Pokemon.serializer(),Bulbasaur))
data class DummyCaptured(
    val dummyCaptured: CapturedPokemon = CapturedPokemon(
        name = "bulbasaur",
        nickname = "Spiney",
        captureDate = Calendar.DATE.toString(),
        capturedLevel = nextInt(0, 100)
    )
)