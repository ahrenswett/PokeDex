package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val capturedPokemonFormat = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    explicitNulls = false
    isLenient = true }

@Serializable
data class CapturedPokemon(
    val name: String,
    val nickname: String?,
    val captureDate: String,
    val capturedLevel: Int,
)