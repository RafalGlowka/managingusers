package com.glowka.rafal.managingusers.presentation.flow.dashboard.list

import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.presentation.architecture.ScreenStructure
import org.koin.core.scope.Scope

object ListScreenStructure : ScreenStructure<EmptyParam, ListViewModelToFlowInterface.Event,
      ListViewModelToFlowInterface, ListViewModelToViewInterface>() {
  override val fragmentClass = ListFragment::class
  override fun Scope.viewModelCreator() = ListViewModelImpl(
    stringResolver = get(),
    searchByNameUseCase = get(),
    refreshUsersUseCase = get(),
  )

}