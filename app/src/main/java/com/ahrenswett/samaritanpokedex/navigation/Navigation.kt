package com.ahrenswett.samaritanpokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.data.api.Api
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListScreen
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListViewModel

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.POKE_LIST.route
    ){
        // Navigation to Main screen
        composable(Routes.POKE_LIST.route){
            //Composable that triggers a navigate to Main Screen UI Event
            PokeListScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                viewModel = PokeListViewModel(Repository(Api()))
            )
        }
        // Composable for Details page
        composable(Routes.POKE_DETAIL.route, arguments = listOf(
            navArgument("pokemonName"){
                type = NavType.StringType
            }
        )){
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
        }
    }
}