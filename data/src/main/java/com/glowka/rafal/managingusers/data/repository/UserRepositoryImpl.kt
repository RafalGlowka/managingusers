package com.glowka.rafal.managingusers.data.repository

import com.apollographql.apollo3.ApolloClient
import com.glowka.rafal.managingusers.data.graphql.CreateUserMutation
import com.glowka.rafal.managingusers.data.graphql.DeleteUserMutation
import com.glowka.rafal.managingusers.data.graphql.UserListQuery
import com.glowka.rafal.managingusers.data.graphql.type.CreateUserInput
import com.glowka.rafal.managingusers.data.graphql.type.DeleteUserInput
import com.glowka.rafal.managingusers.data.mapper.CreateUserToUserMapper
import com.glowka.rafal.managingusers.data.mapper.NodeUserToUserMapper
import com.glowka.rafal.managingusers.data.utils.toUseCaseResultFlow
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import com.glowka.rafal.managingusers.domain.utils.logD
import com.glowka.rafal.managingusers.domain.utils.mapSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@OptIn(kotlinx.coroutines.FlowPreview::class)
class UserRepositoryImpl(
  private val apolloClient: ApolloClient,
  private val nodeUserToUserMapper: NodeUserToUserMapper,
  private val createUserToUserMapper: CreateUserToUserMapper,
) : UserRepository {

  private var users: MutableList<User> = ArrayList()

  override fun clearUserList() {
    users = ArrayList()
  }

  override fun getUsers(): Flow<UseCaseResult<List<User>>> {
    return apolloClient.query(UserListQuery()).toUseCaseResultFlow().mapSuccess { data ->
//      logD("users on server: ${data.users.totalCount}")
      // Pagination during server comminication was not required.
      clearUserList()
      val newElements = data.users.nodes?.filter { userNode ->
        users.firstOrNull { user ->
          user.id == userNode?.id
        } == null
      }?.mapNotNull { user ->
        nodeUserToUserMapper(user!!)
      } ?: emptyList()
      users.addAll(newElements)
      users
    }
  }

  override fun query(query: String): Flow<UseCaseResult<List<User>>> {
    return flow {
      val result = if (query.isEmpty()) {
        users
      } else {
        users.filter { user -> user.name.contains(query) }
      }
      emit(UseCaseResult.Success(result))
    }
  }

  override fun add(
    name: String,
    email: String,
    gender: String,
    status: String
  ): Flow<UseCaseResult<User>> {
    return apolloClient.mutation(
      CreateUserMutation(
        userData = CreateUserInput(
          name = name,
          email = email,
          gender = gender,
          status = status,
        )
      )
    ).toUseCaseResultFlow().flatMapConcat { data ->
      getUsers().flatMapConcat { flowOf(data) }
    }.mapSuccess { data ->
      createUserToUserMapper(data.createUser!!.user!!)
    }
  }

  override fun delete(userId: Int): Flow<UseCaseResult<Boolean>> {
    return apolloClient.mutation(
      DeleteUserMutation(
        userData = DeleteUserInput(
          id = userId
        )
      )
    ).toUseCaseResultFlow()
      .mapSuccess { data ->
        val result = users.removeIf { user ->
          user.id == data.deleteUser?.user?.id
        }
        result
      }
  }

}

