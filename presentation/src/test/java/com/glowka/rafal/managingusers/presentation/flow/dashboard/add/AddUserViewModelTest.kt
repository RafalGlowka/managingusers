package com.glowka.rafal.managingusers.presentation.flow.dashboard.add

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.usecase.AddUserUseCase
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
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

class AddUserViewModelTest : ViewModelTest() {

  @MockK
  private lateinit var addUserUseCase: AddUserUseCase

  private lateinit var viewModel: AddViewModelImpl

  @Before
  fun prepare() {
    initMocks()
    viewModel = AddViewModelImpl(
      addUserUseCase = addUserUseCase,
      toastService = toastService,
      stringResolver = stringResolver,
    )
  }

  @After
  fun finish() {
    confirmVerified(addUserUseCase)
    confirmVerified(toastService)

    unmockkAll()
    clearAllMocks()
  }

  @Test
  fun viewModelOnAddEvent() = runViewModelTest {
    // Given
    val USER_ID = 123
    val USER_NAME = "name"
    val USER_EMAIL = "test.test@test.com"
    val USER_GENDER = "gender"

    val user = User(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    every { addUserUseCase.invoke(any()) } returnsSuccess user

    val eventRecorder =
      EventsRecorder<AddViewModelToFlowInterface.Event>(AddViewModelToFlowInterface.Event.Added(user))
    viewModel.onScreenEvent = eventRecorder::listen

    viewModel.init(EmptyParam.EMPTY)
    // When
    viewModel.state.value = viewModel.state.value.copy(
      fullName = USER_NAME,
      email = USER_EMAIL,
      gender = USER_GENDER
    )
    viewModel.onViewEvent(AddViewModelToViewInterface.ViewEvents.Add)
    advanceUntilIdle()
    println("test1")

    // Than
    eventRecorder.assert()
    verify { addUserUseCase.invoke(any()) }
  }

  @Test
  fun viewModelOnBackEvent() = runViewModelTest {

    val eventRecorder =
      EventsRecorder<AddViewModelToFlowInterface.Event>(AddViewModelToFlowInterface.Event.Back)
    viewModel.onScreenEvent = eventRecorder::listen

    // When
    viewModel.onViewEvent(AddViewModelToViewInterface.ViewEvents.Close)
    advanceUntilIdle()
    println("test2")

    // Than
    eventRecorder.assert()
  }

}