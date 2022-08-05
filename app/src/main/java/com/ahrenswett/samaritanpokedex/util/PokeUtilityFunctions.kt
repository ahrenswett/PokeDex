package com.ahrenswett.samaritanpokedex.util

import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.domain.models.Stat

fun pokeTypeStringBuilder(pokemon: Pokemon):String {
    val typesStr = StringBuilder()
    for (type in pokemon.types) {
        typesStr.append(type.type.name.replaceFirstChar { it.uppercase() })
        if (pokemon.types.size-1 != pokemon.types.indexOf(type)) typesStr.append(" · ")
    }
    return typesStr.toString()
}

fun pokeStats(stats: List<Stat>): List<Pair<String,Int>>{
    return stats.map{ it.stat.name to it.base_stat}
}

