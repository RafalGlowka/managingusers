package com.glowka.rafal.managingusers.modules

import com.glowka.rafal.managingusers.data.mapper.CreateUserToUserMapper
import com.glowka.rafal.managingusers.data.mapper.CreateUserToUserMapperImpl
import com.glowka.rafal.managingusers.data.mapper.NodeUserToUserMapper
import com.glowka.rafal.managingusers.data.mapper.NodeUserToUserMapperImpl
import com.glowka.rafal.managingusers.data.repository.UserRepositoryImpl
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.usecase.AddUserUseCase
import com.glowka.rafal.managingusers.domain.usecase.AddUserUseCaseImpl
import com.glowka.rafal.managingusers.domain.usecase.DeleteUserUseCase
import com.glowka.rafal.managingusers.domain.usecase.DeleteUserUseCaseImpl
import com.glowka.rafal.managingusers.domain.usecase.InitRepositoryUseCase
import com.glowka.rafal.managingusers.domain.usecase.InitRepositoryUseCaseImpl
import com.glowka.rafal.managingusers.domain.usecase.RefreshUsersUseCase
import com.glowka.rafal.managingusers.domain.usecase.RefreshUsersUseCaseImpl
import com.glowka.rafal.managingusers.domain.usecase.SearchByNameUseCase
import com.glowka.rafal.managingusers.domain.usecase.SearchByNameUseCaseImpl
import org.koin.dsl.module

val userModule = module {

  factory<NodeUserToUserMapper> {
    NodeUserToUserMapperImpl()
  }

  factory<CreateUserToUserMapper> {
    CreateUserToUserMapperImpl()
  }

  single<UserRepository> {
    UserRepositoryImpl(
      apolloClient = get(),
      nodeUserToUserMapper = get(),
      createUserToUserMapper = get(),
    )
  }

  factory<InitRepositoryUseCase> {
    InitRepositoryUseCaseImpl(
      userRepository = get()
    )
  }

  factory<RefreshUsersUseCase> {
    RefreshUsersUseCaseImpl(
      userRepository = get()
    )
  }

  factory<SearchByNameUseCase> {
    SearchByNameUseCaseImpl(
      userRepository = get(),
    )
  }

  factory<AddUserUseCase> {
    AddUserUseCaseImpl(
      userRepository = get(),
    )
  }

  factory<DeleteUserUseCase> {
    DeleteUserUseCaseImpl(
      userRepository = get(),
    )
  }

}