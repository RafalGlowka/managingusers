package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import kotlinx.coroutines.flow.Flow

interface InitRepositoryUseCase : UseCase<EmptyParam, List<User>>

class InitRepositoryUseCaseImpl(
  val userRepository: UserRepository,
) : InitRepositoryUseCase {

  override fun invoke(param: EmptyParam): Flow<UseCaseResult<List<User>>> {
    return userRepository.getUsers()
  }
}