package com.glowka.rafal.managingusers.domain.repository

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  fun clearUserList()
  fun getUsers(): Flow<UseCaseResult<List<User>>>
  fun query(query: String): Flow<UseCaseResult<List<User>>>
  fun add(name: String, email: String, gender: String, status: String): Flow<UseCaseResult<User>>
  fun delete(userId: Int): Flow<UseCaseResult<Boolean>>
}