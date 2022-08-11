package com.ahrenswett.samaritanpokedex.ui.poke_details
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.ahrenswett.samaritanpokedex.domain.models.CapturedPokemon
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.util.Constants
import com.ahrenswett.samaritanpokedex.util.pokeStats
import com.ahrenswett.samaritanpokedex.util.pokeTypeStringBuilder
import java.util.*
import kotlin.random.Random.Default.nextInt

/*
This screen shows data about the pokemon a user clicked on in the PokeListScreen
it then gives the option to save the pokemon to a private Json file of captured Pokemon
*/

const val TAG = "PokemonDetailsPage"
//TODO: figure out why pressing back does not allow to go to another pokemon

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PokemonDetails(
    viewModel: PokeDetailsViewModel = hiltViewModel()
){
    val openDialog = remember { mutableStateOf(false) }
    val pokemon = viewModel.pokemon
//    val pokemonCaptureData by remember { mutableStateOf(pokemon!!.capturedData)}
    val scaffoldState = rememberScaffoldState()

    Log.i("Tag" , viewModel.pokemon.toString())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(pokemon = pokemon.value!!)
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
                content = { AboutCard(pokemon = pokemon.value!!) }
            )

            // Base Stats Card Box
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = { BaseStatsCard(pokemon = pokemon.value!!) }
            )
//TODO: figure out why not working compose is supposed to reload
            ButtonOrCaptureInfo(pokemon, openDialog)
//            if (pokemonCaptureData != null) {
////                Show Capture Info
//                CapturedCard(pokemon!!.capturedData!!)
//            } else {
////                Show Capture Button
//                Button(
//                    modifier = Modifier
//                        .padding(20.dp, 10.dp, 20.dp, 10.dp),
//                    shape = RoundedCornerShape(100.dp),
//                    onClick = { openDialog.value = true }
//                ) {
//                    Text(
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(10.dp),
//                        text = "CAPTURE"
//                    )
//                }
//            }

//
            PopUp(openDialog, pokemon.value!!, viewModel)

        }
    }
}

@Composable
fun ButtonOrCaptureInfo(pokemon: MutableState<Pokemon?>, openDialog: MutableState<Boolean>) {
    Log.i(POKE_DETAIL_VM_TAG, "BUTTTTONNNNNN")
    if (pokemon.value!!.capturedData != null) {
//                Show Capture Info
        CapturedCard(pokemon.value!!.capturedData!!)
    } else {
//                Show Capture Button
        Button(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 10.dp),
            shape = RoundedCornerShape(100.dp),
            onClick = { openDialog.value = true }
        ) {
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

/******************************** TopBar Composable ********************************/
@Composable
fun TopBar(pokemon: Pokemon?){
    if (pokemon != null) {
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
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PopUp(openDialog : MutableState<Boolean>, pokemon: Pokemon, viewModel:PokeDetailsViewModel){

    val context = LocalContext.current
    var nickname  by remember { mutableStateOf("")}
    val capturedDate = Calendar.getInstance().toString()
    val capturedLevel = nextInt(1 , 100)

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
                        RoundedCornerShape(100)
                    )
            ){
                Column() {
                    Text(
                        text = "Capturing ${pokemon.name.uppercase()}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                    Capture Level Field
                    TextField(
                        value = capturedLevel.toString(),
                        onValueChange = {},
                        label = {Text(text = "Level")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                  Capture Date Field
                    TextField(
                        value = capturedDate,
                        onValueChange = {},
                        label = { Text(text ="Date Captured")},
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

//                    Nickname Field
                    TextField(
                        value = nickname,
                        onValueChange = {nickname = it},
                        label = { Text(text = "Nickname")},
                        placeholder = {
                            Text(
                                text =  "(optional)",
                                fontSize = 20.sp,
                            )
                                      },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Button(
                        modifier = Modifier
                            .padding(20.dp, 10.dp, 20.dp, 10.dp),
                        shape = RoundedCornerShape(100.dp),
                        onClick = {
                            //Shou
//                            create a capturedPokemon
                            viewModel.pokemon.value!!.capturedData = CapturedPokemon(
                                pokemon.name,
                                nickname,
                                capturedDate,
                                capturedLevel,
                            )

//                                close the dialog
                            openDialog.value = false


//                            change captured from false to ture triggering remove capture button and show capture data

//                                if (File("${context.filesDir}/poke.txt").exists()) {
////                                get it and pas it to VM
//                                } else {
////                                create JsonARRAY with captured pokemon. should move this to app setup and just append to array here
//                                    File("${context.filesDir}/poke.txt").writer()
//                                }

//                                val file =
//                                    context.openFileInput("poke.txt") ?: File("${context.filesDir}")
//                                val jsonArrayString =
//                                    context.openFileInput("poke.txt").bufferedReader()
//                                        .useLines { lines ->
//                                            lines.fold("") { some, text ->
//                                                "$some\n$text"
//                                            }
//                                        }
//                            does file exist?
//                            read it and parse to array

//                            val json = JsonArray.serializer(
//                                Pokemon.serializer(), pokemon)
//                            context.openFileOutput("poke.txt", Context.MODE_PRIVATE).use {
//                                it.write(json.toByteArray())
//                            }
//                            val jsonArrayString = context.openFileInput("poke.txt").bufferedReader().useLines { lines ->
//                                lines.fold("") { some, text ->
//                                    "$some\n$text"
//                                }
//                            }
                        }
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = "CAPTURE"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CapturedCard(data: CapturedPokemon){

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
                text = "Capture Information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
            )
            Text(
                text = "Nickname: ${data.nickname}",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Captured on: ${data.captureDate}",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = "Captured Level: ${data.capturedLevel}",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
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
