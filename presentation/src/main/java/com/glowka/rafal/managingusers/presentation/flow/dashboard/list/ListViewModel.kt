package com.glowka.rafal.managingusers.presentation.flow.dashboard.list

import androidx.compose.runtime.mutableStateOf
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.usecase.RefreshUsersUseCase
import com.glowka.rafal.managingusers.domain.usecase.SearchByNameUseCase
import com.glowka.rafal.managingusers.domain.usecase.UseCaseResult
import com.glowka.rafal.managingusers.domain.utils.EMPTY
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.domain.utils.StringResolver
import com.glowka.rafal.managingusers.domain.utils.collectUseCase
import com.glowka.rafal.managingusers.domain.utils.logD
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseViewModel
import com.glowka.rafal.managingusers.presentation.architecture.ScreenEvent
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.architecture.ViewModelToViewInterface
import com.glowka.rafal.managingusers.presentation.architecture.launch
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToFlowInterface.Event
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToViewInterface.ViewEvents
import com.glowka.rafal.managingusers.presentation.utils.exhaustive
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

interface ListViewModelToFlowInterface : ViewModelToFlowInterface<EmptyParam, Event> {
  sealed class Event : ScreenEvent {
    object AddUser : Event()
    data class DeleteUser(val user: User) : Event()
    object Back : Event()
  }

  fun refresh()
}

interface ListViewModelToViewInterface : ViewModelToViewInterface<State, ViewEvents> {
  sealed class ViewEvents {
    object AddUser : ViewEvents()
    data class DeleteUser(val user: User) : ViewEvents()
    data class Query(val query: String) : ViewEvents()
    data class RefreshList(val query: String) : ViewEvents()
  }

  data class State(
    val statusLabel: String = String.EMPTY,
    val searchQuery: String = String.EMPTY,
    val errorMessage: String = String.EMPTY,
    val isRefreshing: Boolean = false,
    val items: List<User> = emptyList()
  )
}

class ListViewModelImpl(
  private val stringResolver: StringResolver,
  private val searchByNameUseCase: SearchByNameUseCase,
  private val refreshUsersUseCase: RefreshUsersUseCase,
) : ListViewModelToViewInterface, ListViewModelToFlowInterface,
  BaseViewModel<EmptyParam, Event, State, ViewEvents>(
    backPressedEvent = Event.Back
  ) {
  override val state = mutableStateOf(State())

  private var searchJob: Job? = null
  private var lastQuery: String = String.EMPTY

  override fun init(param: EmptyParam) {
    onViewEvent(ViewEvents.Query(lastQuery))
  }

  override fun onViewEvent(event: ViewEvents) {
    when (event) {
      is ViewEvents.DeleteUser -> {
        sendEvent(
          event = Event.DeleteUser(
            user = event.user,
          )
        )
      }
      is ViewEvents.Query -> {
        searchJob = null
        val newValue = event.query
        lastQuery = newValue
        searchJob = launch {
          logD("starting search for $newValue")
          searchByNameUseCase(param = newValue).collectUseCase(
            onSuccess = { list ->
              updateList(list)
            }
          )
        }
      }
      is ViewEvents.AddUser -> {
        sendEvent(
          event = Event.AddUser
        )
      }
      is ViewEvents.RefreshList -> {
        refresh()
      }
    }.exhaustive
  }

  private fun updateList(users: List<User>) {
    logD("updateList ${users.size}")
    if (users.isEmpty()) {
      val query = lastQuery
      val errorMessage = if (query.isEmpty()) {
        stringResolver(R.string.list_is_empty)
      } else {
        stringResolver(R.string.missing_results, query)
      }
      state.value = state.value.copy(
        items = emptyList(),
        errorMessage = errorMessage,
        statusLabel = stringResolver(R.string.status_query, "${users.size}")
      )
    } else {
      state.value = state.value.copy(
        errorMessage = String.EMPTY,
        items = users,
        statusLabel = stringResolver(R.string.status_query, "${users.size}")
      )
    }
  }

  @OptIn(kotlinx.coroutines.FlowPreview::class)
  override fun refresh() {
    launch {
      state.value = state.value.copy(
        isRefreshing = true
      )
      refreshUsersUseCase(
        param = EmptyParam.EMPTY
      ).flatMapConcat { fullListResponse ->
        if (fullListResponse is UseCaseResult.Success<List<User>>) {
          searchByNameUseCase(param = state.value.searchQuery)
        } else {
          flowOf(fullListResponse)
        }
      }.collectUseCase(
        onSuccess = { list ->
          updateList(list)
          state.value = state.value.copy(
            isRefreshing = false
          )
        }
      )
    }
  }

}