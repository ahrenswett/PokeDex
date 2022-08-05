package com.ahrenswett.samaritanpokedex.ui.main_poke_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahrenswett.samaritanpokedex.data.PokemonPagingSource
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


// TODO: Implement pagination utilizing the next URL on the response. Still have to learn how to do that with Compose.
//  Figure out how to return a flow of pokemon and store in a list

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val repo: Repository
): ViewModel(){

    // Get the initial response
    var pokemon : Flow<PagingData<Pokemon>> = Pager(PagingConfig(pageSize = 20)){
        PokemonPagingSource(repository = repo)
    }.flow

    var pager : Pager<String,Pokemon> = Pager(PagingConfig(pageSize = 20)){PokemonPagingSource(repo)}

    // channel to send and receive events from for navigation
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

//  Routes to event based on user input from UI Screen
    fun onEvent(event: PokeListEvents) {
        when (event) {
            is PokeListEvents.OnPokeBallClick -> (sendUiEvent (UiEvent.Navigate(Routes.CATCH_LIST.route)))

            is PokeListEvents.OnPokeClick -> {
//             Feel like I should be able to pass a Pokemon type but for now I will be wasteful and encode decode

                val json = Json.encodeToString(Pokemon.serializer(),event.pokemon)
                sendUiEvent(
                    UiEvent.Navigate(
//                        the equal sign in the wrong place will ruin your day!!!!!
                        Routes.POKE_DETAIL.route + "?pokemon=${json}")
                        .also { Log.i("VM", event.pokemon.name) }
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
