package com.glowka.rafal.managingusers.data.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Operation
import io.mockk.MockKAdditionalAnswerScope
import io.mockk.MockKStubScope
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow

infix fun <D : Operation.Data, B> MockKStubScope<ApolloCall<D>, B>.returnsData(
  data: D
): MockKAdditionalAnswerScope<ApolloCall<D>, B> {
  val resp = ApolloResponse.Builder(
    data = data,
    requestUuid = mockk(),
    operation = mockk(),
  ).build()
  val call: ApolloCall<D> = mockk()
  every { call.toFlow() } returns flow {
    emit(resp)
  }
  return returns(
    call
  )
}

infix fun <D : Operation.Data, B> MockKStubScope<ApolloCall<D>, B>.returnsError(
  errorValue: Error
): MockKAdditionalAnswerScope<ApolloCall<D>, B> {
  val resp = ApolloResponse.Builder(
    data = null as D?,
    requestUuid = mockk(),
    operation = mockk(),
  ).apply {
    errors(listOf(errorValue))
  }.build()
  val call: ApolloCall<D> = mockk()
  every { call.toFlow() } returns flow {
    emit(resp)
  }
  return returns(
    call
  )
}