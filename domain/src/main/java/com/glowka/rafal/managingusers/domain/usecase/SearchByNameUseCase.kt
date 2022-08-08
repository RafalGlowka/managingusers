package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

interface SearchByNameUseCase : UseCase<String, List<User>>

class SearchByNameUseCaseImpl(
  private val userRepository: UserRepository
) : SearchByNameUseCase {

  override fun invoke(param: String): Flow<UseCaseResult<List<User>>> {
    return userRepository.query(query = param)
  }
}