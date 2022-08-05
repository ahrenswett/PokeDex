package com.ahrenswett.samaritanpokedex.ui.main_poke_list


//import com.ahrenswett.samaritanpokedex.util.colors
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.R
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import com.ahrenswett.samaritanpokedex.util.Constants
import com.ahrenswett.samaritanpokedex.util.pokeTypeStringBuilder


/* Shows a list of Pokemon in a grid format.
 * If a user selects one of the items, they are taken to the detail page of that item.
 * Scrolling list loads more items.
 * Floating action Poké ball button takes the user to the list of captured Pokemon.
 */


@Composable
// Gets data from the PokeListViewModel passes user input to PokeListViewModel
fun PokeListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    // should be getting the hilt view model but not showing
    viewModel: PokeListViewModel = hiltViewModel()
){

    // collect the flow<List> to display would rather have it collect flow<Pokemon> into a list this was proving challenging
    val poke = viewModel.pokemon.collectAsLazyPagingItems()
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
            Image( painter = painterResource(id = R.drawable.title_image),
                contentDescription = "Go to captured Poké list",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
            )
                 },
        scaffoldState = scaffoldState,

        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(PokeListEvents.OnPokeBallClick) },
                content = {
                    Image(
                        //TODO: Figure out why icon is not showing properly
                        painter = painterResource(id = R.drawable.poke_ball),
                        contentDescription = "Go to captured Poké list",
                        Modifier
                            .size(64.dp)
                            .then(Modifier.fillMaxWidth())
                            .then(Modifier.fillMaxHeight())
                    )
                },
                backgroundColor = White
            )}
        ){
//        Grid(poke = poke, viewModel = viewModel )

        PokeGrid( pager = viewModel.pager, viewModel)
    }
}



/**************************  Populates the Grid and calls Sub Composables  Option 1 *******************************/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Grid(poke : LazyPagingItems<Pokemon>, viewModel:PokeListViewModel){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        content = {
            items(poke.itemCount){ index ->
                println("Poke List Size ${poke.itemCount}")
                poke[index].let {
                    //show the items
                    PokemonCard(
                        pokemon = it!!,
                        // reference the path to the viewModel and pass it to the item
                        pokeClick = viewModel::onEvent,
                        viewModel = viewModel
                    )
                }
            }
        },
    )
}

/**************************  Populates the Grid and calls Sub Composables Option 2 *******************************/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokeGrid(pager: Pager<String, Pokemon>, viewModel: PokeListViewModel){
    val lazyPagingItems = pager.flow.collectAsLazyPagingItems()

/*
 * TODO: Vertical Grid improvements
 *  Figure out spinning wheel for loading state
 *  Figure out how to store list so that it doesn't fetch from remote each time
 */

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        content = {
            items(
                count = lazyPagingItems.itemCount){ index ->
                println("Poke List Size ${lazyPagingItems.itemCount}")
                lazyPagingItems[index].let {
                    //show the items
                    PokemonCard(
                        pokemon = it!!,
                        // reference the path to the viewModel and pass it to the item
                        pokeClick = viewModel::onEvent,
                        viewModel = viewModel
                    )
                }
            }
        },
    )

}



/*******************  Compsable for Pokemon Card  ********************/
@Composable
fun PokemonCard(pokemon: Pokemon, pokeClick: (PokeListEvents.OnPokeClick) -> Unit, viewModel: PokeListViewModel){
    Card(
    elevation = 10.dp,
    backgroundColor = colorResource( Constants.COLOR_MAP[pokemon.types[0].type.name]!! ), // enum for parsing the colors
    modifier = Modifier
        .padding(8.dp)
        .shadow(10.dp, RoundedCornerShape(10.dp), true)
        .clip(RoundedCornerShape(10.dp))
        .height(150.dp)
        .fillMaxWidth()
        .clickable {
            Log.i("PokeListScreen", pokemon.name)
            viewModel.onEvent(PokeListEvents.OnPokeClick(pokemon))
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            LoadImage(pokemon = pokemon)
            PokemonDescription(pokemon)
        }

    }
}

/*********************** Details banner at bottom of card ***********************/
@Composable
fun PokemonDescription(pokemon: Pokemon){

//    Collect the types in a string to make second line of description
    val typesStr = pokeTypeStringBuilder(pokemon)

    Surface(
        Modifier
            .height(65.dp)
            .fillMaxSize(),
        color = White,

        ){
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "#${pokemon.id} ${pokemon.name.replaceFirstChar { it.uppercase() }}",
                color = Black, textAlign = TextAlign.Left,
            )
            Text(
                text = typesStr,
                color = Black, textAlign = TextAlign.Left
            )
        }

    }
}

/***********************      Coil Image loading      ***********************/
@Composable
fun LoadImage(pokemon: Pokemon){
    SubcomposeAsyncImage(
        model = pokemon.sprites.other.officialArtwork.front_default,
        loading = {
            CircularProgressIndicator()
        },
        contentDescription = "${pokemon.name} image",
        modifier = Modifier.size(74.dp),
        //  Todo: Take care of case of failure
        alignment = Alignment.Center
    )
}

