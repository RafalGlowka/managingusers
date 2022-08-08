package com.glowka.rafal.managingusers.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error
import com.glowka.rafal.managingusers.data.graphql.CreateUserMutation
import com.glowka.rafal.managingusers.data.graphql.DeleteUserMutation
import com.glowka.rafal.managingusers.data.graphql.UserListQuery
import com.glowka.rafal.managingusers.data.mapper.CreateUserToUserMapper
import com.glowka.rafal.managingusers.data.mapper.NodeUserToUserMapper
import com.glowka.rafal.managingusers.data.utils.firstData
import com.glowka.rafal.managingusers.data.utils.returnsData
import com.glowka.rafal.managingusers.data.utils.returnsError
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import com.glowka.rafal.managingusers.domain.utils.EMPTY
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

  @MockK
  private lateinit var apolloClient: ApolloClient

  @MockK
  private lateinit var nodeUserToUserMapper: NodeUserToUserMapper

  @MockK
  private lateinit var createUserToUserMapper: CreateUserToUserMapper

  lateinit var repository: UserRepository

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    repository = UserRepositoryImpl(
      apolloClient = apolloClient,
      createUserToUserMapper = createUserToUserMapper,
      nodeUserToUserMapper = nodeUserToUserMapper,
    )
  }

  @After
  fun finish() {
    confirmVerified(
      apolloClient,
      nodeUserToUserMapper,
      createUserToUserMapper,
    )
    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun checkErrorHandlingQuery() = runBlocking {
    // Given
    val TEST_MESSAGE1 = "Test message1"
    val error1 = Error(
      message = TEST_MESSAGE1,
      null,
      null,
      null,
      null,
    )

    every { apolloClient.query<UserListQuery.Data>(any()) } returnsError error1

    // When
    val response1 = repository.getUsers().first()

    // Then
    Assert.assertEquals(UseCaseResult.Error(message = TEST_MESSAGE1), response1)
    verify { apolloClient.query<UserListQuery.Data>(any()) }
  }

  @Test
  fun checkErrorHandlingAdd() = runBlocking {
    // Given
    val TEST_MESSAGE1 = "Test message1"
    val error1 = Error(
      message = TEST_MESSAGE1,
      null,
      null,
      null,
      null,
    )

    val TEST_MESSAGE2 = "Test message2"
    val error2 = Error(
      message = TEST_MESSAGE2,
      null,
      null,
      null,
      null,
    )

    every { apolloClient.query<UserListQuery.Data>(any()) } returnsError error1
    every { apolloClient.mutation<CreateUserMutation.Data>(any<CreateUserMutation>()) } returnsError error2

    // When
    val response2 = repository.add("","","","").first()

    // Then
    Assert.assertEquals(UseCaseResult.Error(message = TEST_MESSAGE2), response2)
    verify { apolloClient.query<UserListQuery.Data>(any()) }
    verify { apolloClient.mutation<CreateUserMutation.Data>(any()) }
  }


  @Test
  fun checkErrorHandlingDelete() = runBlocking {
    // Given
    val TEST_MESSAGE1 = "Test message1"
    val error1 = Error(
      message = TEST_MESSAGE1,
      null,
      null,
      null,
      null,
    )

    val TEST_MESSAGE3 = "Test message3"
    val error3 = Error(
      message = TEST_MESSAGE3,
      null,
      null,
      null,
      null,
    )
    every { apolloClient.query<UserListQuery.Data>(any()) } returnsError error1
    every { apolloClient.mutation<DeleteUserMutation.Data>(any<DeleteUserMutation>()) } returnsError error3

    // When
    val response3 = repository.delete(1).first()


    // Then
    Assert.assertEquals(UseCaseResult.Error(message = TEST_MESSAGE3), response3)
    verify { apolloClient.mutation<DeleteUserMutation.Data>(any()) }
  }


  @Test
  fun checkingInitializationAndSearch() = runBlocking {
    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"
    val node = UserListQuery.Node(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    val users = UserListQuery.Users(
      nodes = listOf(node),
      totalCount = 1
    )

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    every { nodeUserToUserMapper.invoke(node) } returns user

    val remoteData = UserListQuery.Data(users)

    every { apolloClient.query<UserListQuery.Data>(any()) } returnsData remoteData
    println(repository.getUsers().first())

    // When
    val response = repository.query(String.EMPTY).firstData()

    // Then
    Assert.assertEquals(listOf(user), response)
    verify { apolloClient.query<UserListQuery.Data>(any()) }
    verify { nodeUserToUserMapper(node) }
  }

  @Test
  fun checkingAdding() = runBlocking {
    // Given
    val USER_ID = 13
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"
    val USER_GENDER = "male"
    val USER_STATUS = "active"

    val createUser = CreateUserMutation.User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    val mutationResponse = CreateUserMutation.Data(CreateUserMutation.CreateUser(createUser))

    every { apolloClient.mutation<CreateUserMutation.Data>(any<CreateUserMutation>()) } returnsData mutationResponse

    val TEST_MESSAGE1 = "Test message1"
    val error1 = Error(
      message = TEST_MESSAGE1,
      null,
      null,
      null,
      null,
    )

    every { apolloClient.query<UserListQuery.Data>(any()) } returnsError error1

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    every { createUserToUserMapper.invoke(createUser) } returns user

    // When
    val response = repository.add(USER_NAME, USER_EMAIL, USER_GENDER, USER_STATUS).firstData()

    // Then
    Assert.assertEquals(user, response)
    verify { apolloClient.query<UserListQuery.Data>(any()) }
    verify { apolloClient.mutation<CreateUserMutation.Data>(any()) }
    verify { createUserToUserMapper(createUser) }
  }

}