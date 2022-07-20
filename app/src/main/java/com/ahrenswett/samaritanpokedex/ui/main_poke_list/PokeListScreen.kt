package com.ahrenswett.samaritanpokedex.ui.main_poke_list


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.R
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import kotlinx.coroutines.flow.collect


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

    // collect the flow<List> to display would rather have it collect flow<Pokemon> into a list this was proving challenging
    val poke = viewModel.pokeList.collectAsState(initial = emptyList())

    val scaffoldState = rememberScaffoldState()


    // launches an event triggered by a VM through the Channel in the VM then navigates to appropriate page Via the navigation Page.
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when (event){
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
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
                        println("Poke List Size ${poke.value.size}")
                    poke.value[index].let {
                        //show the items
                        PokemonItem(
                            pokemon =it,
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
            .padding(8.dp, 8.dp, 8.dp, 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(150.dp)
            .fillMaxWidth()
            .clickable(enabled = true) {

                println("Clicked ${pokemon.name}, is a ${pokemon.types[0].type.name} pokemon")
                pokeClick.invoke(PokeListEvents.OnPokeClick(pokemon.url))

            }
    ) {
//        Load Image
        Row() {
            SubcomposeAsyncImage(
                model = pokemon.sprites.other.officialArtwork.front_default,
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "${pokemon.name} image"
//                  Todo: Take care of case of failure
            )
        }

//        Todo: Break into smaller composables and style card with Material theme
//        Print the data on the screen
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Pokemon number ${pokemon.order} is : ",
                color = White, textAlign = TextAlign.Center
            )
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                color = White, textAlign = TextAlign.Center
            )
            for (type in pokemon.types) {
                Text(
                    text = "Type ${type.type.name}".replaceFirstChar { it.uppercase() },
                    color = White, textAlign = TextAlign.Center
                )
            }
        }
    }
}
