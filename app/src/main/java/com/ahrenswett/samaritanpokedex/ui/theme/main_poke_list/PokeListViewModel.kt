package com.ahrenswett.samaritanpokedex.ui.theme.main_poke_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ahrenswett.samaritanpokedex.data.NetworkSource
import com.ahrenswett.samaritanpokedex.modules.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent

class PokeListViewModel(private val networkSource: NetworkSource = NetworkSource("https://pokeapi.co/api/v2/pokemon?limit=20&offset=0") ): ViewModel() {


    var uiState by mutableStateOf(PokeListUiState())
        private set

    val pokeList = networkSource.response!!.results
    val sections = (0 until 25).toList().chunked(5)


    val itemsIndexedList = listOf("A", "B", "C")

    fun onEvent(event: PokeListEvents) {
        when (event) {
            is PokeListEvents.onPokeBallClick -> (
                    UiEvent.Navigate(Routes.CATCH_LIST.route)
                    )
            is PokeListEvents.OnPokeClick -> (
                    UiEvent.Navigate(Routes.POKE_DETAIL.route + "?event.pokeId=${event.pokeId}")
                    )
        }
    }
}
