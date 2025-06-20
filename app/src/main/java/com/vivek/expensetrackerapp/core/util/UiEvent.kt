
package com.vivek.expensetrackerapp.core.util

import com.vivek.expensetrackerapp.presentation.screens.home.BottomSheets

abstract class Event

sealed class UiEvent: Event() {
    data class ShowSnackbar(val uiText: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()

    data class OpenBottomSheet(val bottomSheet: BottomSheets) : UiEvent()


}