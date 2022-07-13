package com.ahrenswett.samaritanpokedex.ui.poke_details
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.navigation.UiEvent

//shows data about the pokemon

//get data from the viewmodel and display it
const val TAG = "PokemonDetailsPage"

@Composable
fun PokemonDetails(
    onNavigate: (UiEvent.Navigate) ->Unit,
    onPopBackStack: () -> Unit,
    viewModel: PokeDetailsViewModel
){
    val scaffoldState = rememberScaffoldState()
    val pokemon = viewModel.pokemon

//    test to see if data is passed. Its not or its going to a different page>:(
    pokemon?.let { Log.i( TAG,pokemon.name)  }

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Card(elevation =  6.dp,
            backgroundColor = Color.LightGray) {
            Column() {
                if (pokemon != null) {
                    Text(text = "$pokemon", color = Color.Black)
                }

            }
            Row() {
                SubcomposeAsyncImage(
                    model = pokemon!!.sprites.other.officialArtwork.front_default,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "${pokemon.name} image",
                )
            }
        }

    }
}