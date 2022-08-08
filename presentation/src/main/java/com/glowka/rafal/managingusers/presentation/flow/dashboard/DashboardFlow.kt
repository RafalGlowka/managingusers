package com.glowka.rafal.managingusers.presentation.flow.dashboard

import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.presentation.architecture.*
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddUserScreenStructure
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteUserScreenStructure
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToFlowInterface
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListScreenStructure
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToFlowInterface

@Suppress("MaxLineLength")
interface DashboardFlow : Flow<EmptyParam, DashboardResult> {

  companion object {
    const val SCOPE_NAME = "Dashboard"
  }

  sealed class Screens<PARAM : Any, EVENT : ScreenEvent,
      VIEWMODEL_TO_FLOW : ViewModelToFlowInterface<PARAM, EVENT>>(screenStructure: ScreenStructure<PARAM, EVENT, VIEWMODEL_TO_FLOW, *>) :
    Screen<PARAM, EVENT, VIEWMODEL_TO_FLOW>(flowScopeName = SCOPE_NAME, screenStructure = screenStructure) {

    object List :
      Screens<EmptyParam, ListViewModelToFlowInterface.Event, ListViewModelToFlowInterface>(
        ListScreenStructure
      )
  }

  sealed class ScreenDialogs<PARAM : Any, EVENT : ScreenEvent,
      VIEWMODEL_TO_FLOW : ViewModelToFlowInterface<PARAM, EVENT>>(screenStructure: ScreenDialogStructure<PARAM, EVENT, VIEWMODEL_TO_FLOW, *>) :
    ScreenDialog<PARAM, EVENT, VIEWMODEL_TO_FLOW>(flowScopeName = SCOPE_NAME, screenStructure = screenStructure) {
      object DeleteUser : ScreenDialogs<DeleteViewModelToFlowInterface.Param, DeleteViewModelToFlowInterface.Event, DeleteViewModelToFlowInterface>(
        DeleteUserScreenStructure
      )
      object AddUser : ScreenDialogs<EmptyParam, AddViewModelToFlowInterface.Event, AddViewModelToFlowInterface>(
        AddUserScreenStructure
      )
  }
}
