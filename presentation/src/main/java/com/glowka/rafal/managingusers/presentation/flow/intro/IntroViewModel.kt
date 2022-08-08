package com.glowka.rafal.managingusers.presentation.flow.intro

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.glowka.rafal.managingusers.domain.service.SnackBarService
import com.glowka.rafal.managingusers.domain.usecase.InitRepositoryUseCase
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.domain.utils.collectUseCase
import com.glowka.rafal.managingusers.domain.utils.onMain
import com.glowka.rafal.managingusers.presentation.architecture.BaseViewModel
import com.glowka.rafal.managingusers.presentation.architecture.ScreenEvent
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToViewInterface
import com.glowka.rafal.managingusers.presentation.architecture.launch
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroViewModelToFlowInterface.Event
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroViewModelToViewInterface.ViewEvents
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay

interface IntroViewModelToFlowInterface : ViewModelToFlowInterface<EmptyParam, Event> {
  sealed class Event : ScreenEvent {
    object Finished : Event()
  }
}

interface IntroViewModelToViewInterface : ViewModelToViewInterface<State, ViewEvents> {
  data class State(
    val emptyState: EmptyParam = EmptyParam.EMPTY
  )

  sealed class ViewEvents {}
}

class IntroViewModelImpl(
  private val snackBarService: SnackBarService,
  private val initRepositoryUseCase: InitRepositoryUseCase
) : IntroViewModelToFlowInterface, IntroViewModelToViewInterface,
  BaseViewModel<EmptyParam, Event, State, ViewEvents>(
    backPressedEvent = null
  ) {

  var animation = false
  var data = false

  override fun init(param: EmptyParam) {
    initialDataFeatch()

    launch {
      delay(3000)
      animation = true
      if (data && animation) showNext()
    }
  }

  private fun initialDataFeatch() {
    launch {
      initRepositoryUseCase(param = EmptyParam.EMPTY).collectUseCase(
        onSuccess = { _ ->
          onMain {
            data = true
            if (data && animation) showNext()
          }
        },
        onError = { error ->
          // Error decoding should be added to show user a call to action message.
          snackBarService.showSnackBar(
            message = error.message,
            duration = Snackbar.LENGTH_INDEFINITE,
            actionLabel = "Retry",
            action = {
              initialDataFeatch()
            }
          )
        }
      )
    }
  }

  private fun showNext() {
    sendEvent(event = Event.Finished)
  }

  override val state: MutableState<State> = mutableStateOf(State())

  override fun onViewEvent(event: ViewEvents) {
    // Nop
  }
}