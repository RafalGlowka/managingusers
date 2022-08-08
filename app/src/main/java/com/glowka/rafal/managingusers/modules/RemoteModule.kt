package com.glowka.rafal.managingusers.modules

import com.apollographql.apollo3.ApolloClient
import com.glowka.rafal.managingusers.data.remote.ApolloClientBuilder
import com.glowka.rafal.managingusers.data.remote.JSONSerializer
import com.glowka.rafal.managingusers.data.remote.JSONSerializerImpl
import com.glowka.rafal.managingusers.data.remote.OKHttpClientBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module

val remoteModule = module {

  factory<OkHttpClient> {
    OKHttpClientBuilder.create()
  }

  single<JSONSerializer> {
    JSONSerializerImpl()
  }

  single<ApolloClient> {
    ApolloClientBuilder.create(okHttpClient = get())
  }

}