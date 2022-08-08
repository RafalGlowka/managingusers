package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.usecase.DeleteUserUseCase.Param
import kotlinx.coroutines.flow.Flow

interface DeleteUserUseCase : UseCase<Param, Boolean> {
  data class Param(
    val user: User,
  )
}

class DeleteUserUseCaseImpl(
  private val userRepository: UserRepository,
) : DeleteUserUseCase {

  override fun invoke(param: Param): Flow<UseCaseResult<Boolean>> {
    return userRepository.delete(userId = param.user.id)
  }
}