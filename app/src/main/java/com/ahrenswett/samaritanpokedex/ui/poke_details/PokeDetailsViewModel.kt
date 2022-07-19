package com.ahrenswett.samaritanpokedex.ui.poke_details

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
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PokeDetailsViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle : SavedStateHandle
):ViewModel(){

    var pokemon by mutableStateOf<Pokemon?>(null)

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    init {
        savedStateHandle.get<String?>(R.string.poke_details_id.toString())
    }

//TODO: Figure out why navigation is sending data. It seem that the screen is not even correct as no hard coded dat loads on the details screen either.





    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch { _uiEvents.send(event) }
    }
}