package com.ahrenswett.samaritanpokedex.ui.poke_details

import com.ahrenswett.samaritanpokedex.domain.models.CapturedPokemon
import java.io.File

sealed class PokeDetailsEvents {
    data class CatchPokemon(
        val capturedPokemon: CapturedPokemon,
        val file: File
        ) : PokeDetailsEvents()
}