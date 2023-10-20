package com.example.composearchitecture.navigation.base

import androidx.compose.runtime.Stable
import androidx.compose.ui.window.DialogProperties

@Stable
interface DialogDestination : NavigationDestination {
    val dialogProperties: DialogProperties
}