package com.glowka.rafal.managingusers.presentation.flow.dashboard.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.lazy.GridCells
//import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.glowka.rafal.managingusers.domain.model.User
import com.glowka.rafal.managingusers.domain.utils.EMPTY
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.BaseFragment
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToViewInterface.State
import com.glowka.rafal.managingusers.presentation.flow.dashboard.list.ListViewModelToViewInterface.ViewEvents
import com.glowka.rafal.managingusers.presentation.style.FontSize
import com.glowka.rafal.managingusers.presentation.style.Fonts
import com.glowka.rafal.managingusers.presentation.style.Margin
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class ListFragment :
  BaseFragment<State, ViewEvents, ListViewModelToViewInterface>() {

  @OptIn(ExperimentalFoundationApi::class)
  override fun ComposeView.renderState(viewModelState: MutableState<State>) {
    setContent {
      MaterialTheme {
        val items = viewModelState.value.items
        var text by rememberSaveable { mutableStateOf(viewModelState.value.searchQuery) }

        AddFlotingButtonLayer(
          fabPosition = FabPosition.End,
          onClick = {
            viewModel.onViewEvent(ViewEvents.AddUser)
          },
          buttonContent = {
            Icon(Icons.Filled.Add, contentDescription = "Add")
          }
        ) {
          Column() {
            TextField(
              value = text,
              onValueChange = { fieldValue ->
                viewModel.onViewEvent(ViewEvents.Query(fieldValue))
                viewModelState.value = viewModelState.value.copy(
                  searchQuery = fieldValue
                )
                text = fieldValue
              },
              label = { Text(getString(R.string.search_by_name_hint)) },
              maxLines = 1,
              modifier = Modifier
                .fillMaxWidth()
                .padding(Margin.small)
            )
            Text(
              modifier = Modifier
                .wrapContentSize()
                .align(Alignment.End),
              textAlign = TextAlign.Center,
              text = viewModelState.value.statusLabel,
              fontSize = FontSize.small
            )
            SwipeRefresh(
              state = rememberSwipeRefreshState(viewModelState.value.isRefreshing),
              onRefresh = { viewModel.onViewEvent(ViewEvents.RefreshList(viewModelState.value.searchQuery)) },
            ) {
              if (items.isNotEmpty()) {
                LazyVerticalGrid(columns = GridCells.Fixed(count = 1)) {
                  items(
                    items.size,
                    null
                  ) {
                    UserItem(items[it])
                  }
                }
              } else {
                Text(
                  modifier = Modifier
                    .fillMaxWidth(),
                  textAlign = TextAlign.Center,
                  text = viewModelState.value.errorMessage,
                )
              }
            }
          }
        }
      }
    }
  }

  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  fun UserItem(user: User) {
    Column(
      Modifier
        .combinedClickable(
          enabled = true,
          onClick = {},
          onLongClick = {
            viewModel.onViewEvent(ViewEvents.DeleteUser(user))
          }
        )
        .padding(5.dp)
    ) {
      Text(
        modifier = Modifier.wrapContentSize(),
        textAlign = TextAlign.Center,
        text = user.name,
        fontFamily = Fonts.BoldFont,
        fontSize = FontSize.large
      )
      Text(
        modifier = Modifier.wrapContentSize(),
        textAlign = TextAlign.Center,
        text = user.email ?: String.EMPTY,
        fontFamily = Fonts.BoldFont,
        fontSize = FontSize.big
      )
    }
  }

  @Composable
  fun AddFlotingButtonLayer(
    fabPosition: FabPosition,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonContent: @Composable () -> Unit,
    screenContent: @Composable (PaddingValues) -> Unit,
  ) {
    Scaffold(
      floatingActionButtonPosition = fabPosition,
      floatingActionButton = {
        FloatingActionButton(
          onClick = onClick,
          modifier = modifier,
          content = buttonContent
        )
      },
      content = screenContent,
    )
  }

}