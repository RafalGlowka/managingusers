package com.glowka.rafal.managingusers.presentation.flow.dashboard.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseDialogFragment
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.add.AddViewModelToViewInterface.ViewEvents
import com.glowka.rafal.managingusers.presentation.style.FontSize
import com.glowka.rafal.managingusers.presentation.style.Fonts
import com.glowka.rafal.managingusers.presentation.style.Margin


class AddUserFragment :
  BaseDialogFragment<State, ViewEvents, AddViewModelToViewInterface>() {

  override fun ComposeView.renderState(viewModelState: MutableState<State>) {
    setContent {
      val focusManager = LocalFocusManager.current

      MaterialTheme {
        Column(
          modifier = Modifier.verticalScroll(rememberScrollState()).padding(Margin.normal)
        ) {
          Text(
            modifier = Modifier
              .wrapContentSize()
              .clickable {
                viewModel.onViewEvent(ViewEvents.Close)
              },
            textAlign = TextAlign.Center,
            text = getString(R.string.add_user_label),
            fontSize = FontSize.base,
            fontFamily = Fonts.NormalFont
          )
          TextField(
            value = viewModelState.value.fullName,
            onValueChange = { fieldValue ->
              viewModelState.value = viewModelState.value.copy(
                fullName = fieldValue
              )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Text,
              imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
              onNext = {
                focusManager.moveFocus(FocusDirection.Next)
              }
            ),
            label = { Text(getString(R.string.add_user_name_label)) },
            maxLines = 1,
            modifier = Modifier
              .fillMaxWidth()
              .padding(Margin.small)
          )
          viewModelState.value.fullNameError?.let { message ->
            Text(
              modifier = Modifier
                .wrapContentSize()
                .padding(Margin.small),
              textAlign = TextAlign.Center,
              text = message,
              fontSize = FontSize.small,
              fontFamily = Fonts.NormalFont,
              color = Color.Red
            )
          }
          Spacer(modifier = Modifier.height(Margin.normal))
          TextField(
            value = viewModelState.value.email,
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Email,
              imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
              onNext = {
                focusManager.moveFocus(FocusDirection.Next)
              }
            ),
            onValueChange = { fieldValue ->
              viewModelState.value = viewModelState.value.copy(
                email = fieldValue
              )
            },
            label = { Text(getString(R.string.add_user_email_label)) },
            maxLines = 1,
            modifier = Modifier
              .fillMaxWidth()
              .padding(Margin.small)
          )
          viewModelState.value.emailError?.let { message ->
            Text(
              modifier = Modifier
                .wrapContentSize()
                .padding(Margin.small),
              textAlign = TextAlign.Center,
              text = message,
              fontSize = FontSize.small,
              fontFamily = Fonts.NormalFont,
              color = Color.Red
            )
          }
          Spacer(modifier = Modifier.height(Margin.normal))
          TextField(
            value = viewModelState.value.gender,
            onValueChange = { fieldValue ->
              viewModelState.value = viewModelState.value.copy(
                gender = fieldValue
              )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
              keyboardType = KeyboardType.Text,
              imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
              onDone = {
                viewModel.onViewEvent(ViewEvents.Add)
              }
            ),
            label = { Text(getString(R.string.add_user_gender_label)) },
            maxLines = 1,
            modifier = Modifier
              .fillMaxWidth()
              .padding(Margin.small)
          )
          viewModelState.value.genderError?.let { message ->
            Text(
              modifier = Modifier
                .wrapContentSize()
                .padding(Margin.small),
              textAlign = TextAlign.Center,
              text = message,
              fontSize = FontSize.small,
              fontFamily = Fonts.NormalFont,
              color = Color.Red
            )
          }
          Spacer(modifier = Modifier.height(Margin.normal))
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
              text = "Cancel",
              fontSize = FontSize.big,
              fontFamily = Fonts.BoldFont
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(
              modifier = Modifier
                .wrapContentSize()
                .clickable {
                  viewModel.onViewEvent(ViewEvents.Add)
                },
              textAlign = TextAlign.Center,
              text = "Add",
              fontSize = FontSize.big,
              fontFamily = Fonts.BoldFont
            )

          }

        }
      }
    }
  }

}