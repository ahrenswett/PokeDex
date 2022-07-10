package com.ahrenswett.samaritanpokedex.data

import dagger.Provides
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.http.*

//      TODO: handle a bad address add validation to HTTPClient currently expects success

val PokeHttpClient : HttpClient by lazy { HttpClient()}