package com.glowka.rafal.managingusers.domain.usecase

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.repository.UserRepository
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

class SearchByNameUseCaseTest {

  @MockK
  private lateinit var userRepository: UserRepository

  lateinit var useCase: SearchByNameUseCase

  @Before
  fun prepare() {
    MockKAnnotations.init(this)
    useCase = SearchByNameUseCaseImpl(
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
    val query = "Mike"
    val user: User = mockk()

    every { userRepository.query(query = query) } returnsSuccess listOf(user)

    // When
    val response = useCase(param = query).firstData()

    // Than
    Assert.assertEquals(listOf(user), response)
    verify { userRepository.query(query = query) }
  }
}