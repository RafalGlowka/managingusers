package com.glowka.rafal.managingusers.presentation.flow.dashboard.delete

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.service.ToastService
import com.glowka.rafal.managingusers.domain.usecase.DeleteUserUseCase
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import com.glowka.rafal.managingusers.domain.utils.EMPTY
import com.glowka.rafal.managingusers.presentation.architecture.BaseViewModel
import com.glowka.rafal.managingusers.presentation.architecture.ScreenEvent
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToViewInterface
import com.glowka.rafal.managingusers.presentation.architecture.launch
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToFlowInterface.Event
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToFlowInterface.Param
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToViewInterface.ViewEvents


interface DeleteViewModelToFlowInterface : ViewModelToFlowInterface<Param, Event> {
  data class Param(val user: User)
  sealed class Event : ScreenEvent {
    object Back : Event()
    object Deleted : Event()
  }
}

interface DeleteViewModelToViewInterface : ViewModelToViewInterface<State, ViewEvents> {
  data class State(
    val user: User
  )

  sealed class ViewEvents {
    object Close : ViewEvents()
    object Delete : ViewEvents()
  }
}

class DeleteViewModelImpl(
  private val deleteUserUseCase: DeleteUserUseCase,
  private val toastService: ToastService,
) : DeleteViewModelToViewInterface, DeleteViewModelToFlowInterface,
  BaseViewModel<Param, Event, State, ViewEvents>(
    backPressedEvent = Event.Back
  ) {

  override val state: MutableState<State> = mutableStateOf(State(User(0, String.EMPTY, String.EMPTY)))

  lateinit var param: Param

  override fun init(param: Param) {
    this.param = param
    state.value = state.value.copy(
      user = param.user
    )
  }

  override fun onViewEvent(event: ViewEvents) {
    when (event) {
      ViewEvents.Close -> {
        launch {
          sendEvent(event = Event.Back)
        }
      }
      ViewEvents.Delete ->
        launch {
          deleteUserUseCase(param = DeleteUserUseCase.Param(param.user)).collect { result ->
            when(result) {
              is UseCaseResult.Success<Boolean> -> {
                if (result.data) {
                  sendEvent(Event.Deleted)
                } else {

                }
              }
              is UseCaseResult.Error -> {
                toastService.showMessage(result.message)
              }
            }
          }
        }

    }

  }

}