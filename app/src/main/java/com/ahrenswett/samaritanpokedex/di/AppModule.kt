package com.ahrenswett.samaritanpokedex.di

import com.ahrenswett.samaritanpokedex.data.PokeHttpClient
import com.ahrenswett.samaritanpokedex.data.Repository
import com.ahrenswett.samaritanpokedex.data.api.NetworkSource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // this provides the Client to the Repo
    @Singleton
    @Provides
    fun providesHttp():NetworkSource{
        return(NetworkSource(PokeHttpClient))
    }

    // this provides the Network source
    @Singleton
    @Provides
    fun provideNetworkSource(): Repository{
        return(Repository(NetworkSource()))
    }

}