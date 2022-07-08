package com.ahrenswett.samaritanpokedex.data

import io.ktor.client.*

//      TODO: handle a bad address add validation to HTTPClient currently expects success
val PokeHttpClient : HttpClient by lazy { HttpClient() }
