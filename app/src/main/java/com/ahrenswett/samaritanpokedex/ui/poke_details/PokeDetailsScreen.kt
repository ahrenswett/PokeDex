package com.ahrenswett.samaritanpokedex.ui.poke_details
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

@Composable
fun PokemonDetails(
    onNavigate: (UiEvent.Navigate) ->Unit,
    onPopBackStack: () -> Unit,
    viewModel: PokeDetailsViewModel
){
    val scaffoldState = rememberScaffoldState()
    val pokemon = viewModel.pokemon

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        TopAppBar(title = {
            if (pokemon != null) Text(text = pokemon.name)
            else Text(text = "NOFACE")
        })
        Card(elevation =  6.dp,
            backgroundColor = Color.LightGray) {
            Column() {
                if (pokemon != null) {
                    pokemon.url?.let { it1 -> Text(text = it1) }
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