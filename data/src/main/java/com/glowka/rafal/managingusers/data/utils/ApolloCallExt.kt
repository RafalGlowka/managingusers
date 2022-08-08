package com.glowka.rafal.managingusers.data.utils

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Operation
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <DATA : Operation.Data> ApolloCall<DATA>.toUseCaseResultFlow(): Flow<UseCaseResult<DATA>> {
  return this
    .toFlow()
    .map { response ->
      val errorMessage = response.errors?.let { errors ->
        if (errors.isNotEmpty()) {
          errors[0].message
        } else {
          null
        }
      }
      if (errorMessage != null) {
        UseCaseResult.Error(errorMessage)
      } else {
        response.data?.let { data ->
          UseCaseResult.Success(data)
        } ?: UseCaseResult.Error("Missing data")
      }
    }.catch { e ->
      emit(UseCaseResult.Error(e.message ?: "Unknown error"))
    }
}
