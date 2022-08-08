package com.glowka.rafal.managingusers.presentation.flow.dashboard.delete

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.usecase.DeleteUserUseCase
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

class DeleteUserViewModelTest : ViewModelTest() {

  @MockK
  private lateinit var deleteUserUseCase: DeleteUserUseCase

  private lateinit var viewModel: DeleteViewModelImpl

  @Before
  fun prepare() {
    initMocks()
    viewModel = DeleteViewModelImpl(
      deleteUserUseCase = deleteUserUseCase,
      toastService = toastService,
    )
  }

  @After
  fun finish() {
    confirmVerified(deleteUserUseCase)
    confirmVerified(toastService)

    unmockkAll()
    clearAllMocks()
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

    every { deleteUserUseCase.invoke(DeleteUserUseCase.Param(user)) } returnsSuccess true

    val eventRecorder =
      EventsRecorder<DeleteViewModelToFlowInterface.Event>(DeleteViewModelToFlowInterface.Event.Deleted)
    viewModel.onScreenEvent = eventRecorder::listen

    viewModel.init(DeleteViewModelToFlowInterface.Param(user))
    // When
    viewModel.onViewEvent(DeleteViewModelToViewInterface.ViewEvents.Delete)
    advanceUntilIdle()
    println("test1")

    // Than
    eventRecorder.assert()
    verify { deleteUserUseCase.invoke(any()) }
  }

  @Test
  fun viewModelOnCloseEvent() = runViewModelTest {

    val eventRecorder =
      EventsRecorder<DeleteViewModelToFlowInterface.Event>(DeleteViewModelToFlowInterface.Event.Back)
    viewModel.onScreenEvent = eventRecorder::listen

    // When
    viewModel.onViewEvent(DeleteViewModelToViewInterface.ViewEvents.Close)
    advanceUntilIdle()
    println("test2")

    // Than
    eventRecorder.assert()
  }

}