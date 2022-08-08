package com.glowka.rafal.managingusers.modules.flow

import com.glowka.rafal.managingusers.presentation.architecture.businessFlow
import com.glowka.rafal.managingusers.presentation.architecture.screen
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroFlow
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroFlowImpl
import org.koin.dsl.module

val introFeatureModule = module {

  single<IntroFlow> {
    IntroFlowImpl(dashboardFlow = get())
  }

  businessFlow(
    scopeName = IntroFlow.SCOPE_NAME,
  ) {
    screen(screen = IntroFlow.Screens.Start)
  }

}