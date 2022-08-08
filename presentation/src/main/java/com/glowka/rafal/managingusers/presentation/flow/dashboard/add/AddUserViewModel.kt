package com.glowka.rafal.managingusers.presentation.flow.dashboard.add

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.service.ToastService
import com.glowka.rafal.managingusers.domain.usecase.AddUserUseCase
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import com.glowka.rafal.managingusers.domain.utils.EMPTY
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.domain.utils.StringResolver
import com.glowka.rafal.managingusers.presentation.BuildConfig
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseViewModel
import com.glowka.rafal.managingusers.presentation.architecture.ScreenEvent
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToViewInterface
import com.glowka.rafal.managingusers.presentation.architecture.launch
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToFlowInterface.Event
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToViewInterface.ViewEvents
import com.glowka.rafal.managingusers.presentation.validator.allSuccess
import com.glowka.rafal.managingusers.presentation.validator.composed.EmailValidator
import com.glowka.rafal.managingusers.presentation.validator.composed.GenderValidator
import com.glowka.rafal.managingusers.presentation.validator.composed.UserNameValidator

interface AddViewModelToFlowInterface : ViewModelToFlowInterface<EmptyParam, Event> {
  sealed class Event : ScreenEvent {
    object Back : Event()
    data class Added(val user: User) : Event()
  }

  fun restart()
}

interface AddViewModelToViewInterface : ViewModelToViewInterface<State, ViewEvents> {
  data class State(
    val fullName: String = String.EMPTY,
    val fullNameError: String? = null,
    val email: String = String.EMPTY,
    val emailError: String? = null,
    val gender: String = "Male",
    val genderError: String? = null,
  )

  sealed class ViewEvents {
    object Close : ViewEvents()
    object Add : ViewEvents()
  }
}

class AddViewModelImpl(
  private val addUserUseCase: AddUserUseCase,
  private val toastService: ToastService,
  private val stringResolver: StringResolver,
) : AddViewModelToViewInterface, AddViewModelToFlowInterface,
  BaseViewModel<EmptyParam, Event, State, ViewEvents>(
    backPressedEvent = Event.Back
  ) {

  override val state: MutableState<State> = mutableStateOf(State())

  val emailValidator = EmailValidator(
    stringResolver(R.string.email_required), stringResolver(R.string.incorrect_email_format)
  ) { errorMessage ->
    state.value = state.value.copy(
      emailError = errorMessage
    )
  }
  val userNameValidator = UserNameValidator(
    stringResolver(R.string.user_name_length_error), stringResolver(R.string.user_name_length_error)
  ) { errorMessage ->
    state.value = state.value.copy(
      fullNameError = errorMessage
    )
  }
  val genderValidator = GenderValidator(
    stringResolver(R.string.gender_length_error), stringResolver(R.string.gender_length_error)
  ) { errorMessage ->
    state.value = state.value.copy(
      genderError = errorMessage
    )
  }

  override fun init(param: EmptyParam) {
    if (BuildConfig.DEBUG) {
      state.value = state.value.copy(
        fullName = "Anna Tester",
        email = "some.mail@some.server",
        gender = "female"
      )
    }
  }

  override fun restart() {
    state.value = State()
  }

  override fun onViewEvent(event: ViewEvents) {
    when (event) {
      ViewEvents.Close -> {
        launch {
          sendEvent(event = Event.Back)
        }
      }
      ViewEvents.Add -> {
        allSuccess(
          userNameValidator.validate(state.value.fullName),
          emailValidator.validate(state.value.email),
          genderValidator.validate(state.value.gender)
        ) { userName, email, gender ->
          launch {
            addUserUseCase(
              param = AddUserUseCase.Param(
                name = userName,
                email = email,
                gender = gender,
              )
            ).collect { result ->
              when (result) {
                is UseCaseResult.Success -> {
                  sendEvent(Event.Added(result.data))
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

  }
}

