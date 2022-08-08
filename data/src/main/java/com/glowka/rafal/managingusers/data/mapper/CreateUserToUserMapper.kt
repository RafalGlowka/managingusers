package com.glowka.rafal.managingusers.data.mapper

import com.glowka.rafal.managingusers.data.graphql.CreateUserMutation
import com.glowka.rafal.managingusers.domain.model.User

interface CreateUserToUserMapper : Mapper<CreateUserMutation.User, User>

class CreateUserToUserMapperImpl : CreateUserToUserMapper {
  override fun invoke(data: CreateUserMutation.User): User {
    return User(
      id = data.id, // Any exception will be catch in mapSuccess method and remapped to error.
      name = data.name,
      email = data.email
    )
  }
}