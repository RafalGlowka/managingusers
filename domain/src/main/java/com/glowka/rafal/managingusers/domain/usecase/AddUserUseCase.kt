package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.usecase.AddUserUseCase.Param
import kotlinx.coroutines.flow.Flow

interface AddUserUseCase : UseCase<Param, User> {
  data class Param(
    val name: String,
    val email: String,
    val gender: String,
  )
}

class AddUserUseCaseImpl(
  private val userRepository: UserRepository,
) : AddUserUseCase {

  override fun invoke(param: Param): Flow<UseCaseResult<User>> {
    return userRepository.add(
      name = param.name,
      email = param.email,
      gender = param.gender,
      status = "active"
    )
  }
}