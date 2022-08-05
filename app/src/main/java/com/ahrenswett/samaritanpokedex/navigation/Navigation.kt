package com.ahrenswett.samaritanpokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.data.api.Api
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListScreen
import com.ahrenswett.samaritanpokedex.ui.main_poke_list.PokeListViewModel
import com.ahrenswett.samaritanpokedex.ui.poke_details.PokemonDetails

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.POKE_LIST.route
    ){

/************************* Home Poke List ****************************/
        composable(Routes.POKE_LIST.route){
            //Composable that triggers a navigate to Main Screen UI Event
            PokeListScreen(
                onNavigate = {
                    navController.navigate(it.route)
                },
                viewModel = PokeListViewModel(Repository(Api()))
            )
        }

/************************* Details page ****************************/
        composable(Routes.POKE_DETAIL.route + "?pokemon={pokemon}",
            arguments = listOf(
                navArgument(name = "pokemon"){
                    type = NavType.StringType
                    defaultValue = ":)"
                },
        )){
            PokemonDetails(
                onPopBackStack = {navController.popBackStack()},
                onCapture = {navController.navigate(it.route)}
            )

        }

/************************* Catch List ****************************/
        composable(Routes.CATCH_LIST.route,
            arguments = listOf(
                navArgument(name = "catchList"){
                    type = NavType.StringType
                    defaultValue = ""
                }
            )){

        }

    }
}