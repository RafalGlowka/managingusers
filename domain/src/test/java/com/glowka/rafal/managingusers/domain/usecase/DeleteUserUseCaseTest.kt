package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
import com.glowka.rafal.managingusers.domain.utils.firstData
import com.glowka.rafal.managingusers.domain.utils.returnsSuccess
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteUserUseCaseTest {

  @MockK
  private lateinit var userRepository: UserRepository

  lateinit var useCase: DeleteUserUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = DeleteUserUseCaseImpl(
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
    val params = DeleteUserUseCase.Param(user = user)

    every { userRepository.delete(USER_ID) } returnsSuccess true

    // When
    val response = useCase(param = params).firstData()

    // Than
    Assert.assertEquals(true, response)
    verify { userRepository.delete(USER_ID) }
  }
}