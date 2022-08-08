package com.glowka.rafal.managingusers.presentation.flow.dashboard.delete

import android.graphics.Color
import com.glowka.rafal.managingusers.presentation.architecture.ScreenDialogStructure
import org.koin.core.scope.Scope

object DeleteUserScreenStructure : ScreenDialogStructure<DeleteViewModelToFlowInterface.Param,
    DeleteViewModelToFlowInterface.Event, DeleteViewModelToFlowInterface,
    DeleteViewModelToViewInterface>(
    statusBarColor = Color.TRANSPARENT,
    lightTextColor = false,
  ) {
  override val fragmentClass = DeleteUserFragment::class
  override fun Scope.viewModelCreator() = DeleteViewModelImpl(
    deleteUserUseCase = get(),
    toastService = get(),
  )
}