package com.ahrenswett.samaritanpokedex.ui.poke_details
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import com.ahrenswett.samaritanpokedex.util.Constants
import com.ahrenswett.samaritanpokedex.util.pokeStats
import com.ahrenswett.samaritanpokedex.util.pokeTypeStringBuilder
import kotlinx.serialization.json.Json

//shows data about the pokemon

//get data from the viewmodel and display it
const val TAG = "PokemonDetailsPage"

//TODO: figure out why pressing back does not allow to go to another pokemon

@Composable
fun PokemonDetails(
    onCapture: (UiEvent.Navigate) ->Unit,
    onPopBackStack: () -> Unit,
    viewModel: PokeDetailsViewModel = hiltViewModel()
){
    val pokemon by remember {mutableStateOf(viewModel.pokemon.value)}
    val scaffoldState = rememberScaffoldState()
    Log.i("Tag" , viewModel.pokemon.toString())

//
//
//    LaunchedEffect(key1 = true){
//        viewModel.uiEvent.collect { event ->
//            when(event){
//                is UiEvent.Navigate -> onNavigate(event)
//                is UiEvent.PopBackStack -> onPopBackStack()
//                else -> Unit
//            }
//        }
//    }


    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(260.dp)
                    .background(
                        colorResource(Constants.COLOR_MAP.get(pokemon!!.types[0].type.name)!!)
                    )
                    .fillMaxWidth()
                    .padding(5.dp),

                content = {
                    Alignment.Center
                    Arrangement.Center
                    TitleBox(pokemon = pokemon!!)
                }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { AboutCard(pokemon = pokemon!!) }
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { BaseStatsCard(pokemon = pokemon!!) }
            )

        }


    }

}

@Composable
fun TitleBox(pokemon: Pokemon){

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = pokemon.sprites.other.officialArtwork.front_default,
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = "${pokemon.name} image",
            //  Todo: Take care of case of failure
            alignment = Alignment.Center
        )
        Text(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            text = "#${pokemon.id} ${pokemon.name}",
            color = Color.White
        )
    }

}

@Composable
fun AboutCard(pokemon: Pokemon){
//    build up the type string
    val typesStr = pokeTypeStringBuilder(pokemon)

    Card(
        modifier = Modifier
            .width(520.dp)
            .padding(20.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        elevation = 5.dp,

    ) {
        Column() {
            //could put in string resources
            Text(
                text = "About",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
            )
            Text(
                text = "Type(s) $typesStr",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Weight: ${pokemon.weight}",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Height: ${pokemon.height}",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Composable
fun BaseStatsCard(pokemon: Pokemon){

    val stats  = pokeStats(pokemon.stats)

    Card(
        modifier = Modifier
            .width(520.dp)
            .padding(20.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        elevation = 5.dp,

        ) {
        Column() {
            //could put in string resources
            Text(
                text = "Base Stats",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
            )
            stats.forEach{pair ->
                Text(
                    text = "${pair.first.uppercase()}: ${pair.second}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

        }
    }

}