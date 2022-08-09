package com.ahrenswett.samaritanpokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahrenswett.samaritanpokedex.data.api.Api
import com.ahrenswett.samaritanpokedex.domain.models.Pokemon
import com.ahrenswett.samaritanpokedex.domain.models.PokemonAddresses
import com.ahrenswett.samaritanpokedex.domain.models.Response
import com.ahrenswett.samaritanpokedex.util.Constants

const val STARTING_KEY = 1

class PokemonPagingSource(
    private val repository: Repository,
):PagingSource<String, Pokemon>() {

// Should take the next from initial URL
//    and return the response to a flow that would auto load the next items in the Grid
     override suspend fun load(params: LoadParams<String>): LoadResult<String, Pokemon> {
        val pageUrl = params.key ?: Constants.BASE_URL
        return try {
            val response = repository.getPokemonListResponse(pageUrl)
            repository.response = response // may not need will delete if find a why just to pass the next easy
            val pokeList = repository.getListItems(response.results)


            LoadResult.Page(
                data = pokeList,
                prevKey = response.previous,
                nextKey = response.next
             )
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }




    //TODO figure this out
    override fun getRefreshKey(state: PagingState<String, Pokemon>): String? {
        return null
    }


}