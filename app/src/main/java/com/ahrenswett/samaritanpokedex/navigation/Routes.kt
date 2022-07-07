package com.ahrenswett.samaritanpokedex.navigation

sealed class Routes(val route:String) {
    object POKE_LIST : Routes("poke_list")
    object POKE_DETAIL : Routes("poke_detail")
    object CATCH_LIST : Routes("catch_list")
}
