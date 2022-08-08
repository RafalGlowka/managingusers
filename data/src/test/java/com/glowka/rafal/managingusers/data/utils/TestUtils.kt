package com.glowka.rafal.managingusers.data.utils

import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

suspend fun <DATA : Any> Flow<UseCaseResult<DATA>>.firstData(): DATA? {

  return when (val response = this.first()) {
    is UseCaseResult.Success<DATA> -> {
      response.data
    }
    else -> null
  }
}