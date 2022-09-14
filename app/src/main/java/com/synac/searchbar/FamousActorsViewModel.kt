package com.synac.searchbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FamousActorsViewModel : ViewModel() {

    var state by mutableStateOf(ActorsScreenState())

    private var searchJob: Job? = null

    fun onAction(userAction: UserAction) {
        when (userAction) {
            UserAction.CloseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
            }
            UserAction.SearchIconClicked -> {
                state = state.copy(isSearchBarVisible = true)
            }
            is UserAction.TextFieldInput -> {
                state = state.copy(searchText = userAction.text)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    searchActorsInList(searchQuery = userAction.text)
                }
            }
            UserAction.SortIconClicked -> {
                state = state.copy(isSortMenuVisible = true)
            }
            UserAction.SortMenuDismiss -> {
                state = state.copy(isSortMenuVisible = false)
            }
            is UserAction.SortItemClicked -> {
                when(userAction.type) {
                    SortType.A2Z -> sortActorsListA2Z()
                    SortType.Z2A -> sortActorsListZ2A()
                    SortType.NONE -> sortActorsListNone()
                }
            }
        }
    }

    private fun sortActorsListNone() {
        state = state.copy(
            list = actorsList,
            isSortMenuVisible = false
        )
    }

    private fun sortActorsListA2Z() {
        val newList = actorsList.sorted()
        state = state.copy(
            list = newList,
            isSortMenuVisible = false
        )
    }

    private fun sortActorsListZ2A() {
        val newList = actorsList.sorted().reversed()
        state = state.copy(
            list = newList,
            isSortMenuVisible = false
        )
    }

    private fun searchActorsInList(
        searchQuery: String
    ) {
        val newList = actorsList.filter {
            it.contains(searchQuery, ignoreCase = true)
        }
        state = state.copy(list = newList)
    }
}


sealed class UserAction {
    object SearchIconClicked : UserAction()
    object CloseIconClicked : UserAction()
    object SortIconClicked : UserAction()
    object SortMenuDismiss : UserAction()
    data class TextFieldInput(val text: String) : UserAction()
    data class SortItemClicked(val type: SortType) : UserAction()
}

enum class SortType {
    A2Z,
    Z2A,
    NONE
}

data class ActorsScreenState(
    val searchText: String = "",
    val list: List<String> = actorsList,
    val isSearchBarVisible: Boolean = false,
    val isSortMenuVisible: Boolean = false,
)