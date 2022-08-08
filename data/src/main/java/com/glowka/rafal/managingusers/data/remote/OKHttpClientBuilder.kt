package com.glowka.rafal.managingusers.data.remote

import okhttp3.OkHttpClient

object OKHttpClientBuilder {

  const val TOKEN = "58fc583c19c80a1bb1a6961536286044497e26f202c465f15c2e64ddf477e459"

  fun create(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.addInterceptor { chain ->
      val request = chain.request()
      val requestWithHeader = request.newBuilder()
        .header("Authorization", "Bearer $TOKEN")
        .build()
      chain.proceed(requestWithHeader)
    }
    return builder.build()
  }
}