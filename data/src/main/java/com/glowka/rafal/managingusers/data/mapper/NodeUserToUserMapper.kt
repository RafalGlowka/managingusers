package com.glowka.rafal.managingusers.data.mapper

import com.glowka.rafal.managingusers.data.graphql.UserListQuery
import com.glowka.rafal.managingusers.domain.model.User

interface NodeUserToUserMapper : Mapper<UserListQuery.Node, User>

class NodeUserToUserMapperImpl : NodeUserToUserMapper {
  override fun invoke(data: UserListQuery.Node): User {
    return User(
      id = data.id, // Any exception will be catch in mapSuccess method and remapped to error.
      name = data.name,
      email = data.email
    )
  }
}