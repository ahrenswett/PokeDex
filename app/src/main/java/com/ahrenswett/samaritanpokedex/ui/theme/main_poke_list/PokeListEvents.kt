package com.ahrenswett.samaritanpokedex.ui.theme.main_poke_list


/**
 * Declare the events on the UI and what they should pass PokeListViewModel
 */
sealed class PokeListEvents {
    data class OnPokeClick(val pokeId: String) : PokeListEvents()
    // Go to captured pokemon view via Poke Ball Click
    object onPokeBallClick : PokeListEvents()
}