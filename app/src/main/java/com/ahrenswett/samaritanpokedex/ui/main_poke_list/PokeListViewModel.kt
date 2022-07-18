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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// TODO: Implement pagination utilizing the next URL on the response. Still have to learn how to do that with Compose.
//  Figure out how to return a flow of pokemon and store in a list

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repo: Repository
): ViewModel(){

    // Get the initial response
    var pokemonAddressResponse = repo.baseResponse?.results!!

    //Get the subsequent Pokemon responses this should store in a list and only receive 1 poke at a time but having a little trouble with that method.
    val pokemonFlowList: Flow<Pokemon> = flow {
            emit(repo.getListItems(pokemonAddressResponse))
    }

    // channel to send and receive events from for navigation
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

//  Routs to appropriate screen.
    fun onEvent(event: PokeListEvents) {
        when (event) {
            is PokeListEvents.onPokeBallClick -> (
                    sendUiEvent (UiEvent.Navigate(Routes.CATCH_LIST.route))
                    )
            is PokeListEvents.OnPokeClick -> {
                sendUiEvent(
                    UiEvent.Navigate(Routes.POKE_DETAIL.route + "?=poke${event.pokeURL}")
                )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }


}
