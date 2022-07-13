package com.ahrenswett.samaritanpokedex.ui.poke_details

sealed class PokeDetailsEvent {
    object CatchPokemon: PokeDetailsEvent()
}