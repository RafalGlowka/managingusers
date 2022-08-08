package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.domain.utils.firstData
import com.glowka.rafal.managingusers.domain.utils.returnsSuccess
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class InitRepositoryUseCaseTest {

  @MockK
  private lateinit var userRepository: UserRepository

  lateinit var useCase: InitRepositoryUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = InitRepositoryUseCaseImpl(
      userRepository = userRepository
    )
  }

  @After
  fun finish() {
    confirmVerified(userRepository)

    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun useCaseIsCallingRepositories() = runBlocking {
    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )
    val params = EmptyParam.EMPTY

    every { userRepository.getUsers() } returnsSuccess listOf(user)

    // When
    val response = useCase(param = params).firstData()

    // Than
    Assert.assertEquals(listOf(user), response)
    verify { userRepository.getUsers() }
  }
}