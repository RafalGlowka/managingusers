package com.glowka.rafal.managingusers.presentation.flow.dashboard

import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.presentation.architecture.BaseFlow
import com.glowka.rafal.managingusers.presentation.architecture.Screen
import com.glowka.rafal.managingusers.presentation.architecture.getViewModelToFlow
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.utils.exhaustive

sealed class DashboardResult {
  object Terminated : DashboardResult()
}

class DashboardFlowImpl :
  BaseFlow<EmptyParam, DashboardResult>(flowScopeName = DashboardFlow.SCOPE_NAME), DashboardFlow {

  override fun onStart(param: EmptyParam): Screen<*, *, *> {
    return showScreenDialog(
      screen = DashboardFlow.Screens.List,
      param = EmptyParam.EMPTY
    ) { event ->
      when (event) {
        is ListViewModelToFlowInterface.Event.DeleteUser -> deleteUser(event.user)
        ListViewModelToFlowInterface.Event.Back -> finish(result = DashboardResult.Terminated)
        ListViewModelToFlowInterface.Event.AddUser -> addUser()
      }.exhaustive
    }
  }

  private fun addUser() {
    // TODO: Adding user should be a separate flow. Then clearing state would be done by the koin scope.
    getViewModelToFlow(DashboardFlow.ScreenDialogs.AddUser).restart()

    showScreenDialog(
      screen = DashboardFlow.ScreenDialogs.AddUser,
      param = EmptyParam.EMPTY
    ) { result ->
      when(result) {
        is AddViewModelToFlowInterface.Event.Added -> {
          hideScreenDialog(DashboardFlow.ScreenDialogs.AddUser)
          getViewModelToFlow(DashboardFlow.Screens.List).refresh()
        }
        AddViewModelToFlowInterface.Event.Back -> {
          hideScreenDialog(DashboardFlow.ScreenDialogs.AddUser)
        }
      }.exhaustive

    }
  }

  private fun deleteUser(user: User) {
    showScreenDialog(
      screen = DashboardFlow.ScreenDialogs.DeleteUser,
      param = DeleteViewModelToFlowInterface.Param(
        user = user,
      ),
    ) { event ->
      when (event) {
        DeleteViewModelToFlowInterface.Event.Back -> {
          hideScreenDialog(DashboardFlow.ScreenDialogs.DeleteUser)
        }
        DeleteViewModelToFlowInterface.Event.Deleted -> {
          hideScreenDialog(DashboardFlow.ScreenDialogs.DeleteUser)
          getViewModelToFlow(DashboardFlow.Screens.List).refresh()
        }
      }.exhaustive
    }
  }

}