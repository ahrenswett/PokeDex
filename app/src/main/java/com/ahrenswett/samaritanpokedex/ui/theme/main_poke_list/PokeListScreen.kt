package com.ahrenswett.samaritanpokedex.ui.theme.main_poke_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ahrenswett.samaritanpokedex.navigation.UiEvent


/* Shows a list of Pokemon in a grid format.
 * If a user selects one of the items, they are taken to the detail page of that item.
 * Scrolling list loads more items.
 * Floating action Poké ball button takes the user to the list of captured Pokemon.
 */


@Composable
// Gets data from the PokeListViewModel passes user input to PokeListViewModel
fun PokeListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: PokeListViewModel = hiltViewModel()
){

    LaunchedEffect(key1 = true){
        // TODO: Define
}

}