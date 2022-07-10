package com.ahrenswett.samaritanpokedex.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name : String,
    val nickname: String? = null,
    val capturedDate: String,
    val capturedLevel: Int,
    val detailData: String
)
