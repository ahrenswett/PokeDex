package com.ahrenswett.samaritanpokedex.ui.main_poke_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahrenswett.samaritanpokedex.R
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent


/* Shows a list of Pokemon in a grid format.
 * If a user selects one of the items, they are taken to the detail page of that item.
 * Scrolling list loads more items.
 * Floating action Poké ball button takes the user to the list of captured Pokemon.
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
// Gets data from the PokeListViewModel passes user input to PokeListViewModel
fun PokeListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,

    // should be getting the hilt view model but not showing
    viewModel: PokeListViewModel // = hiltViewModel()
){

    // collect the flow to display
    val poke = viewModel.pokemonFlowList.collectAsState(initial = emptyList())


    val itemModifier = Modifier
        .border(1.dp, Black, RoundedCornerShape(5.dp))
        .height(80.dp)
        .wrapContentSize()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        // TODO: Define

    }

    Scaffold(
        topBar = {
            Icon(
                painter = painterResource(id = R.drawable.title_image),
                contentDescription = "Go to captured Poké list",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
            )
                 },
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(PokeListEvents.onPokeBallClick) },
                content = {
                    Icon(
                        //TODO: Figure out why icon is not showing properly
                        painter = painterResource(id = R.drawable.poke_ball),
                        contentDescription = "Go to captured Poké list",
                        Modifier
                            .size(48.dp)
                            .alpha(1F)
                            .then(Modifier.fillMaxWidth())
                            .then(Modifier.fillMaxHeight()),
                    )
                }

            )}
        ){
//        Create a lazy grid this should use paging to call for another response need to learn how to implement
        LazyVerticalGrid(
            // TODO: figure out network calls based on items loaded in the grid
            cells = GridCells.Fixed(2),
            content = {
                items(poke.value.size){ index ->
                    poke.value[index].let {
                        //show the items
                        PokemonItem(
                            pokemon = it,
                            // reference the path to the viewModel and pass it to the item
                            pokeClick = viewModel::onEvent

                        )
                    }
                }
                      },
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        )
    }
}
@Composable
fun PokemonItem(pokemon: Pokemon, pokeClick: (PokeListEvents.OnPokeClick) -> Unit ){
    Card(
        elevation = 20.dp,
        backgroundColor = Black,
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(150.dp)
            .fillMaxWidth()
            .clickable(enabled = true) { pokeClick.invoke(PokeListEvents.OnPokeClick(pokeID = pokemon.order!!)) }
    ) {

    }
}
