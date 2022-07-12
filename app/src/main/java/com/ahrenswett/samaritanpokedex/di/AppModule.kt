package com.ahrenswett.samaritanpokedex.di

import com.ahrenswett.samaritanpokedex.data.PokeHttpClient
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.data.api.Api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // this provides the Client to the Repo
    @Singleton
    @Provides
    fun providesHttp():Api{
        return(Api(PokeHttpClient))
    }

    // this provides the Network source
    @Singleton
    @Provides
    fun provideNetworkSource(): Repository{
        return(Repository(Api()))
    }

}