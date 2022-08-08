package com.glowka.rafal.managingusers.presentation.flow.intro

import com.glowka.rafal.managingusers.domain.utils.EmptyParam
import com.glowka.rafal.managingusers.presentation.architecture.*


@Suppress("MaxLineLength")
interface IntroFlow : Flow<EmptyParam, IntroResult> {

  companion object {
    const val SCOPE_NAME = "Intro"
  }

  sealed class Screens<PARAM : Any, EVENT : ScreenEvent, VIEWMODEL_TO_FLOW : ViewModelToFlowInterface<PARAM, EVENT>>(screenStructure: ScreenStructure<PARAM, EVENT, VIEWMODEL_TO_FLOW, *>) :
    Screen<PARAM, EVENT, VIEWMODEL_TO_FLOW>(flowScopeName = SCOPE_NAME, screenStructure = screenStructure) {
    object Start : Screens<EmptyParam, IntroViewModelToFlowInterface.Event, IntroViewModelToFlowInterface>(screenStructure = IntroScreenStructure)
  }
}