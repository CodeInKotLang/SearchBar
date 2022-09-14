package com.synac.searchbar

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synac.searchbar.ui.theme.BackgroundColor
import com.synac.searchbar.ui.theme.CardBackgroundColor
import com.synac.searchbar.ui.theme.cairoFont

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FamousActorsScreen(
    viewModel: FamousActorsViewModel
) {

    val state = viewModel.state

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp)
    ) {
        Crossfade(
            targetState = state.isSearchBarVisible,
            animationSpec = tween(durationMillis = 500)
        ) {
            if (it) {
                SearchAppBar(
                    onCloseIconClicked = {
                        viewModel.onAction(UserAction.CloseIconClicked)
                    },
                    onInputValueChange = { newText ->
                        viewModel.onAction(
                            UserAction.TextFieldInput(newText)
                        )
                    },
                    text = state.searchText,
                    onSearchClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            } else {
                TopAppBar(
                    onSearchIconClicked = {
                        viewModel.onAction(UserAction.SearchIconClicked)
                    },
                    onSortIconClicked = {
                        viewModel.onAction(UserAction.SortIconClicked)
                    },
                    onSortMenuDismiss = {
                        viewModel.onAction(UserAction.SortMenuDismiss)
                    },
                    isSortMenuVisible = state.isSortMenuVisible,
                    onSortItemA2ZClicked = {
                        viewModel.onAction(
                            UserAction.SortItemClicked(SortType.A2Z)
                        )
                    },
                    onSortItemZ2AClicked = {
                        viewModel.onAction(
                            UserAction.SortItemClicked(SortType.Z2A)
                        )
                    },
                    onSortItemNoneClicked = {
                        viewModel.onAction(
                            UserAction.SortItemClicked(SortType.NONE)
                        )
                    }
                )
            }
        }

        Divider(
            color = CardBackgroundColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        LazyColumn {
            items(state.list) { actor ->
                SingleItemCard(
                    actorName = actor
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun SearchAppBar(
    onCloseIconClicked: () -> Unit,
    onInputValueChange: (String) -> Unit,
    text: String,
    onSearchClicked: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            onInputValueChange(it)
        },
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp
        ),
        placeholder = {
            Text(
                text = "Search...",
                fontFamily = cairoFont,
                color = Color.White.copy(alpha = ContentAlpha.medium)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color.White.copy(
                    alpha = ContentAlpha.medium
                )
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        onInputValueChange("")
                    } else {
                        onCloseIconClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White.copy(
                alpha = ContentAlpha.medium
            ),
            focusedBorderColor = Color.White,
            cursorColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchClicked() }
        )
    )
}

@Composable
fun SingleItemCard(
    actorName: String
) {
    Card(
        backgroundColor = CardBackgroundColor,
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_people),
                contentDescription = "People Icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = actorName,
                fontSize = 20.sp,
                fontFamily = cairoFont
            )
        }
    }
}

@Composable
fun TopAppBar(
    onSearchIconClicked: () -> Unit,
    onSortIconClicked: () -> Unit,
    onSortMenuDismiss: () -> Unit,
    onSortItemA2ZClicked: () -> Unit,
    onSortItemZ2AClicked: () -> Unit,
    onSortItemNoneClicked: () -> Unit,
    isSortMenuVisible: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Famous Actors",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onSortIconClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_sort),
                contentDescription = "Sort Icon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
            DropdownMenu(
                expanded = isSortMenuVisible,
                onDismissRequest = onSortMenuDismiss
            ) {
                DropdownMenuItem(onClick = onSortItemA2ZClicked) {
                    Text(text = "Sort  A -> Z")
                }
                DropdownMenuItem(onClick =onSortItemZ2AClicked) {
                    Text(text = "Sort  Z -> A")
                }
                DropdownMenuItem(onClick = onSortItemNoneClicked) {
                    Text(text = "No Sort")
                }
            }
        }
        IconButton(onClick = onSearchIconClicked) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun Prev() {
    FamousActorsScreen(
        viewModel = FamousActorsViewModel()
    )
}










