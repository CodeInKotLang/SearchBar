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
        }
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
    data class TextFieldInput(val text: String) : UserAction()
}

data class ActorsScreenState(
    val searchText: String = "",
    val list: List<String> = actorsList,
    val isSearchBarVisible: Boolean = false
)