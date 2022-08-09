package com.ahrenswett.samaritanpokedex.ui.poke_details
import android.provider.ContactsContract
import android.util.Log
import android.widget.PopupWindow
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.navigation.UiEvent
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListEvents
import com.ahrenswett.samaritanpokedex.util.Constants
import com.ahrenswett.samaritanpokedex.util.DummyPoke
import com.ahrenswett.samaritanpokedex.util.pokeStats
import com.ahrenswett.samaritanpokedex.util.pokeTypeStringBuilder
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.FileOutputStream
import java.io.FileWriter

/*
This screen shows data about the pokemon a user clicked on in the PokeListScreen
it then gives the option to save the pokemon to a private Json file of captured Pokemon
*/

const val TAG = "PokemonDetailsPage"
//TODO: figure out why pressing back does not allow to go to another pokemon

@Composable
fun PokemonDetails(
     onPopBackStack: () -> Unit,
     onCapture: (UiEvent.Navigate) -> Unit,
    viewModel: PokeDetailsViewModel = hiltViewModel()
){
    val capturePokemon = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val pokemon by remember {mutableStateOf(viewModel.pokemon.value)}
    val scaffoldState = rememberScaffoldState()
    Log.i("Tag" , viewModel.pokemon.toString())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(pokemon = pokemon!!)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // About Card BOX
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { AboutCard(pokemon = pokemon!!) }
            )

            // Base Stats Card Box
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { BaseStatsCard(pokemon = pokemon!!) }
            )

            // Capture Button
            Button(
                modifier = Modifier
                    .padding(20.dp,10.dp,20.dp,10.dp),
                shape = RoundedCornerShape(100.dp),
                onClick = {openDialog.value = true}
            ){
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "CAPTURE"
                )
            }

            PopUp(openDialog, pokemon!!, onPopBackStack)

        }
    }
}

/******************************** TopBar Composable ********************************/
@Composable
fun TopBar(pokemon: Pokemon){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(Constants.COLOR_MAP[pokemon.types[0].type.name]!!)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
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

/******************************** About Card Composable ********************************/
@Composable
fun AboutCard(pokemon: Pokemon){
//    build up the type string
    val typesStr = pokeTypeStringBuilder(pokemon)

    Card(
        modifier = Modifier
            .width(520.dp)
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        elevation = 5.dp,

    ) {
        Column() {
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


/******************************** Base Stats Card Composable ********************************/
@Composable
fun BaseStatsCard(pokemon: Pokemon){

    val stats  = pokeStats(pokemon.stats)

    Card(
        modifier = Modifier
            .width(520.dp)
            .padding(20.dp, 10.dp, 20.dp, 10.dp)
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
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


/******************************** PopUp Composable ********************************/
@Composable
fun PopUp(openDialog : MutableState<Boolean>, pokemon: Pokemon, onPopBackStack: () -> Unit){
    var nickname by remember { mutableStateOf("")}
    var capturedDate by remember { mutableStateOf("")}
    var capturedLevel by remember { mutableStateOf("")}

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
        ) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        RoundedCornerShape(40)
                    )
            ){
                Column() {
                    Text(
                        text = "Capturing ${pokemon.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                    Nickname Field
                    TextField(
                        value = nickname,
                        onValueChange = {nickname = it},
                        placeholder = {
                            Text(
                                text =  "Nickname",
                                fontSize = 20.sp,
                            )
                                      },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                  Capture Date Field
                    TextField(
                        value = capturedDate,
                        onValueChange = {capturedDate = it},
                        placeholder = {
                             Text(
                                 text = "Captured Date",
                                 fontSize = 20.sp,
                             )
                                      },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                    Capture Level Field
                    TextField(
                        value = capturedLevel,
                        onValueChange = {capturedLevel = it},
                        placeholder = {
                            Text(
                                text = "Captured Level",
                                fontSize = 20.sp,
                            )
                                      },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                    Capture Button in dialog
                    Button(
                        modifier = Modifier
                            .padding(20.dp,10.dp,20.dp,10.dp),
                        shape = RoundedCornerShape(100.dp),
                        onClick = {
                            val pokeJson = Json.encodeToString(Pokemon.serializer(),pokemon)
//                            if a file does not exist
                            val file = "pokeCatchList"
                            val outputStream = FileWriter(file)
                            outputStream.write(pokeJson)
                            onPopBackStack()


//                            create it
//                            otherwise read from it
//                            then append new poke data to it
//                            write back to file

                        }
                    ){
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "CAPTURE"
                        )
                    }
                }
            }
        }
    }
}


















/*************************** Preview  **********************************/
//TODO: Figure out why not working
//@Preview
//@Composable
//fun DetailsPreview(){
//    val pokemon = DummyPoke().bulbasaur
//
//    Scaffold(
//    ) {
//        Column(
//            Modifier.fillMaxSize(),
//            Arrangement.Center,
//            Alignment.CenterHorizontally
//        ) {
//            Box(
//                modifier = Modifier
//                    .height(260.dp)
//                    .background(
//                        colorResource(Constants.COLOR_MAP[pokemon.types[0].type.name]!!)
//                    )
//                    .fillMaxWidth()
//                    .padding(5.dp),
//
//                content = {
//                    Alignment.Center
//                    Arrangement.Center
//                    TopBar(pokemon = pokemon)
//                }
//            )
//
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                content = { AboutCard(pokemon = pokemon) }
//            )
//
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                content = { BaseStatsCard(pokemon = pokemon) }
//            )
//
//
//
//        }
//
//
//    }
//
//}
