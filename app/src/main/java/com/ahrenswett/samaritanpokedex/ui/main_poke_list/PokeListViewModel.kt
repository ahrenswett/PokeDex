package com.ahrenswett.samaritanpokedex.ui.main_poke_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repo: Repository
): ViewModel(){

    // Get the initial response
    var pokemonAddressResponse = repo.baseResponse?.results!!

    //Get the subsequent Pokemon responses this should store in a list and only receive 1 poke at a time but having a little trouble with that method.
    val pokemonFlowList: Flow<List<Pokemon>> = flow {
            emit(repo.getListItems(pokemonAddressResponse))
    }


    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()


    fun onEvent(event: PokeListEvents) {
        when (event) {
            is PokeListEvents.onPokeBallClick -> (
                    sendUiEvent (UiEvent.Navigate(Routes.CATCH_LIST.route))
                    )
            is PokeListEvents.OnPokeClick -> (
                    sendUiEvent(UiEvent.Navigate(Routes.POKE_DETAIL.route + "?=poke${event.poke}"))
                    )
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}
