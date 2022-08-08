package com.glowka.rafal.managingusers.presentation.flow.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseFragment
import com.glowka.rafal.managingusers.presentation.style.Fonts
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.intro.IntroViewModelToViewInterface.ViewEvents


class IntroFragment : BaseFragment<State, ViewEvents, IntroViewModelToViewInterface>() {

  override fun ComposeView.renderState(viewModelState: MutableState<State>) {
    setContent {
      MaterialTheme {
        Column(
          modifier = Modifier.padding(10.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.users))
          LottieAnimation(
            modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.4f),
            composition = composition,
            iterations = LottieConstants.IterateForever,
          )
          Text(
            text = getString(R.string.app_name),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontFamily = Fonts.BoldFont
          )
        }
      }
    }
  }

}