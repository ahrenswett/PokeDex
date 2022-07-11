package com.ahrenswett.samaritanpokedex.ui.main_poke_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repo: Repository
): ViewModel(){

    var pokemonaddressresponse = repo.baseResponse?.results!!

    val pokemonList  = flow { pokemonaddressresponse
        .forEach(){ address ->
            emit(repo.getListItems(address.url))
        }
    }

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()




    fun onEvent(event: PokeListEvents) {
        when (event) {
            is PokeListEvents.onPokeBallClick -> (
                    sendUiEvent (UiEvent.Navigate(Routes.CATCH_LIST.route))
                    )
            is PokeListEvents.OnPokeClick -> (
                    sendUiEvent(UiEvent.Navigate(Routes.POKE_DETAIL.route + "?event.pokeId=${event.pokeId}"))
                    )
        }
    }


    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch { _uiEvents.send(event) }
    }

}
