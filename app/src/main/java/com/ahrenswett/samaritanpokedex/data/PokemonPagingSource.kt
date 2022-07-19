package com.ahrenswett.samaritanpokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.util.Constants

//class PokemonPagingSource(
//    private val repository :Repository
//    ):PagingSource<Int, Pokemon>() {
//
//    override suspend fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
//        return try {
//            val nextPage = params.key ?: Constants.BASE_URL
//            val pokemonListResponse = repository.getPokemonListResponse(/*needs to be a string  */).next
//
//            LoadResult(
//                data =
//            )
//
//        }catch (e:Exception){
//            LoadResult.Error(e)
//        }
//    }
//
//}