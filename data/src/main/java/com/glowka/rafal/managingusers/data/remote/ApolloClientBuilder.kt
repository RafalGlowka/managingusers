package com.glowka.rafal.managingusers.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object ApolloClientBuilder {

  private const val BASE_URL = "https://gorest.co.in/public/v2/graphql/"

  fun create(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.Builder()
      .serverUrl(BASE_URL)
      .okHttpClient(okHttpClient = okHttpClient)
      .build()
  }

}