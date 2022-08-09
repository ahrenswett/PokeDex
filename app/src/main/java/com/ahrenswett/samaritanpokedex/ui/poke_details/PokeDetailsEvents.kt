package com.ahrenswett.samaritanpokedex.ui.poke_details

import com.ahrenswett.samaritanpokedex.domain.models.Pokemon

sealed class PokeDetailsEvents {
    data class CatchPokemon(val pokemon: Pokemon) : PokeDetailsEvents()
}