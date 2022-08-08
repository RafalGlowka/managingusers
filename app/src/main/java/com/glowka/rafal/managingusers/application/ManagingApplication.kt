package com.glowka.rafal.managingusers.application

import android.app.Application
import com.glowka.rafal.managingusers.domain.utils.logD
import com.glowka.rafal.managingusers.modules.modulesList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ManagingApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    initDI()
  }

  private fun initDI() {
    startKoin {
      androidLogger()
      androidContext(this@ManagingApplication)
      modules(modulesList)
      createEagerInstances()
    }
    logD("Koin initialized")
  }
}