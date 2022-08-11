package com.ahrenswett.samaritanpokedex.ui.poke_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.domain.models.CapturedPokemon
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
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


    fun onEvent(event: PokeDetailsEvents) {
        when (event) {
            is PokeDetailsEvents.CatchPokemon -> {
                val json = Json.encodeToString(CapturedPokemon.serializer(), event.capturedPokemon)
            }
        }
    }


    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch { _uiEvents.send(event) }
    }
}