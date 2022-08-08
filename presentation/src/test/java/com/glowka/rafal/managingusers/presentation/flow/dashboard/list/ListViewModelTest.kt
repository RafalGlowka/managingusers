package com.glowka.rafal.managingusers.presentation.flow.dashboard.list

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.usecase.RefreshUsersUseCase
import com.glowka.rafal.managingusers.domain.usecase.SearchByNameUseCase
import com.glowka.rafal.managingusers.presentation.ViewModelTest
import com.glowka.rafal.managingusers.presentation.utils.EventsRecorder
import com.glowka.rafal.managingusers.presentation.utils.returnsSuccess
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Before
import org.junit.Test

class ListViewModelTest : ViewModelTest() {

  @MockK
  private lateinit var searchByNameUseCase: SearchByNameUseCase

  @MockK
  private lateinit var refreshUsersUseCase: RefreshUsersUseCase

  private lateinit var viewModel: ListViewModelImpl

  @Before
  fun prepare() {
    initMocks()
    viewModel = ListViewModelImpl(
      searchByNameUseCase = searchByNameUseCase,
      refreshUsersUseCase = refreshUsersUseCase,
      stringResolver = stringResolver,
    )
  }

  @After
  fun finish() {
    confirmVerified(searchByNameUseCase)
    confirmVerified(refreshUsersUseCase)
    confirmVerified(toastService)

    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun viewModelOnAddUserEvent() = runViewModelTest {
    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    val eventRecorder =
      EventsRecorder<ListViewModelToFlowInterface.Event>(ListViewModelToFlowInterface.Event.AddUser)
    viewModel.onScreenEvent = eventRecorder::listen

//    viewModel.init(EmptyParam.EMPTY)
    // When
    viewModel.onViewEvent(ListViewModelToViewInterface.ViewEvents.AddUser)
    advanceUntilIdle()
    println("test1")

    // Than
    eventRecorder.assert()
  }

  @Test
  fun viewModelOnDeleteEvent() = runViewModelTest {
    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    val eventRecorder = EventsRecorder<ListViewModelToFlowInterface.Event>(
      ListViewModelToFlowInterface.Event.DeleteUser(user)
    )
    viewModel.onScreenEvent = eventRecorder::listen

    // When
    viewModel.onViewEvent(ListViewModelToViewInterface.ViewEvents.DeleteUser(user = user))
    advanceUntilIdle()
    println("test2")

    // Than
    eventRecorder.assert()
  }

  @Test
  fun viewModelOnQueryEvent() = runViewModelTest {
    // Given
    val QUERY = "Anna"

    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    every { searchByNameUseCase.invoke(any()) } returnsSuccess listOf(user)

    // When
    viewModel.onViewEvent(ListViewModelToViewInterface.ViewEvents.Query(QUERY))
    advanceUntilIdle()

    // Than
    verify { searchByNameUseCase.invoke(any()) }
  }

  @Test
  fun viewModelOnRefreshEvent() = runViewModelTest {
    val QUERY = "Anna"

    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    every { refreshUsersUseCase.invoke(any()) } returnsSuccess listOf(user)
    every { searchByNameUseCase.invoke(any()) } returnsSuccess listOf(user)

    // When
    viewModel.onViewEvent(ListViewModelToViewInterface.ViewEvents.RefreshList(QUERY))
    advanceUntilIdle()

    // Than
    verify { searchByNameUseCase.invoke(any()) }
    verify { refreshUsersUseCase.invoke(any()) }
  }

}