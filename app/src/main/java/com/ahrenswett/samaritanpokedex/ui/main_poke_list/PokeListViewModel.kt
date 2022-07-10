package com.ahrenswett.samaritanpokedex.ui.main_poke_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.data.api.NetworkSource
import com.ahrenswett.samaritanpokedex.domain.models.Response
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import com.ahrenswett.samaritanpokedex.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repo: Repository

): ViewModel(){

    // Used to get details of specific Pokemon
    val pokeMap = repo.baseResponse?.results?.map{it.name; it.url}

    var uiState by mutableStateOf(PokeListUiState())
        private set


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
