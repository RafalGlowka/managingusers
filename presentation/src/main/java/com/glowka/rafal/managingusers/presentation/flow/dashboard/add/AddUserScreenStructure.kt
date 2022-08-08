package com.glowka.rafal.managingusers.presentation.flow.dashboard.add

import android.graphics.Color
import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.presentation.architecture.ScreenDialogStructure
import org.koin.core.scope.Scope

object AddUserScreenStructure : ScreenDialogStructure<EmptyParam,
    AddViewModelToFlowInterface.Event, AddViewModelToFlowInterface,
    AddViewModelToViewInterface>(
    statusBarColor = Color.TRANSPARENT,
    lightTextColor = false,
  ) {
  override val fragmentClass = AddUserFragment::class
  override fun Scope.viewModelCreator() = AddViewModelImpl(
    addUserUseCase = get(),
    toastService = get(),
    stringResolver = get(),
  )
}