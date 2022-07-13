package com.ahrenswett.samaritanpokedex.ui.poke_details

import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDetailsViewModel @Inject constructor(
    savedStateHandle : SavedStateHandle
):ViewModel(){
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    val pokemon = savedStateHandle.get<Pokemon>("poke")






    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch { _uiEvents.send(event) }
    }
}