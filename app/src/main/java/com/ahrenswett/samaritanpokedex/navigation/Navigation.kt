package com.ahrenswett.samaritanpokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrenswett.samaritanpokedex.ui.theme.main_poke_list.PokeListScreen
import com.ahrenswett.samaritanpokedex.ui.theme.main_poke_list.PokeListViewModel

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
                viewModel = PokeListViewModel())
        }
    }

}