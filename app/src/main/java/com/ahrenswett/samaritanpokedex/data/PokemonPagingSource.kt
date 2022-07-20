//package com.ahrenswett.samaritanpokedex.data
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
//import com.ahrenswett.samaritanpokedex.util.Constants
//import kotlin.math.max
//
//const val STARTING_KEY = 0
//
//class PokemonPagingSource(
//    private val repository: Repository
//):PagingSource<Int, Pokemon>() {
//
//    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
//        val anchorPosition = state.anchorPosition ?: return null
//        val pokemon = state.closestItemToPosition(anchorPosition) ?: return null
//        return ensureValidKey(key = pokemon.id - (state.config.pageSize /2))
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
//        return try {
//            val start = params.key ?: STARTING_KEY
//            val range = start.until(start + params.loadSize)
//
//
//            return LoadResult.Page(
//                data = range.map { number ->
//
//                prevKey = when(start){
//                    STARTING_KEY -> null
//                    else -> ensureValidKey(key = range.first - params.loadSize)
//                },
//                nextKey = range.last - 1
//            )
//
//        }catch (e:Exception){
//            LoadResult.Error(e)
//        }
//    }
//
//    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
//
//}