package com.glowka.rafal.managingusers.modules.flow

import com.glowka.rafal.managingusers.presentation.architecture.businessFlow
import com.glowka.rafal.managingusers.presentation.architecture.screen
import com.glowka.rafal.managingusers.presentation.architecture.screenDialog
import com.glowka.rafal.managingusers.presentation.flow.dashboard.DashboardFlow
import com.glowka.rafal.managingusers.presentation.flow.dashboard.DashboardFlowImpl
import org.koin.dsl.module

val dashboardFeatureModule = module {

  single<DashboardFlow> {
    DashboardFlowImpl()
  }

  businessFlow(
    scopeName = DashboardFlow.SCOPE_NAME,
  ) {
    screen(screen = DashboardFlow.Screens.List)
    screenDialog(screen = DashboardFlow.ScreenDialogs.AddUser)
    screenDialog(screen = DashboardFlow.ScreenDialogs.DeleteUser)
  }

}