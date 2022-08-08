package com.glowka.rafal.managingusers.modules

import com.glowka.rafal.managingusers.modules.flow.dashboardFeatureModule
import com.glowka.rafal.managingusers.modules.flow.introFeatureModule
import org.koin.core.module.Module

val modulesList = listOf<Module>(
  appModule,
  remoteModule,
  userModule,
  introFeatureModule,
  dashboardFeatureModule
)