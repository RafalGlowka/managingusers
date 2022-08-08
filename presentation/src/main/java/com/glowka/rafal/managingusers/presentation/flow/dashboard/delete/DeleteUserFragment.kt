package com.glowka.rafal.managingusers.presentation.flow.dashboard.delete

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseDialogFragment
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.delete.DeleteViewModelToViewInterface.ViewEvents
import com.glowka.rafal.managingusers.presentation.style.FontSize
import com.glowka.rafal.managingusers.presentation.style.Fonts
import com.glowka.rafal.managingusers.presentation.style.Margin

class DeleteUserFragment :
  BaseDialogFragment<State, ViewEvents, DeleteViewModelToViewInterface>() {

  override fun ComposeView.renderState(viewModelState: MutableState<State>) {
    setContent {
      MaterialTheme {
        Column(
          modifier = Modifier.verticalScroll(rememberScrollState()).padding(Margin.normal)
        ) {
          Text(
            modifier = Modifier.wrapContentSize(),
            textAlign = TextAlign.Center,
            text = getString(R.string.delete_user, viewModelState.value.user.name),
            fontSize = FontSize.base,
            fontFamily = Fonts.NormalFont
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
          ) {
            Text(
              modifier = Modifier
                .wrapContentSize()
                .clickable {
                  viewModel.onViewEvent(ViewEvents.Close)
                },
              textAlign = TextAlign.Center,
              text = getString(R.string.no),
              fontSize = FontSize.big,
              fontFamily = Fonts.BoldFont
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
              modifier = Modifier
                .wrapContentSize()
                .clickable {
                  viewModel.onViewEvent(ViewEvents.Delete)
                },
              textAlign = TextAlign.Center,
              text = getString(R.string.yes),
              fontSize = FontSize.big,
              fontFamily = Fonts.BoldFont
            )
          }
        }
      }
    }
  }
}