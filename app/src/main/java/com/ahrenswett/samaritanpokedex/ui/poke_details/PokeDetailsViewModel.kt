package com.ahrenswett.samaritanpokedex.ui.poke_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahrenswett.samaritanpokedex.R
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.Routes
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


const val POKE_DETAIL_VM_TAG = "PokeDetailsViewModel"

@HiltViewModel
class PokeDetailsViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle : SavedStateHandle
):ViewModel(){

    var pokemon = mutableStateOf<Pokemon?>(Json.decodeFromString(Pokemon.serializer(),savedStateHandle.get<String>("pokemon")!!))
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()


    init {
        Log.i(
            "PokeDetailsViewModel",
            "${savedStateHandle.keys()}, ${savedStateHandle.get<String?>("pokemon")}"
        )
    }

    fun onEvent(event: PokeDetailsEvents) {
        when (event) {
            is PokeDetailsEvents.CatchPokemon -> {
                val json = Json.encodeToString(
                    Pokemon.serializer(),
                    event.pokemon
            )
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
        viewModelScope.launch { _uiEvents.send(event) }
    }
}