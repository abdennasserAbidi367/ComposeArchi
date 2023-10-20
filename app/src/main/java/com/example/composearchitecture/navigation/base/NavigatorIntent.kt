package com.example.composearchitecture.navigation.base

import androidx.compose.runtime.Stable
import androidx.navigation.NavOptionsBuilder

@Stable
sealed interface NavigatorIntent {

    object NavigateUp : NavigatorIntent

    object PopCurrentBackStack : NavigatorIntent

    data class PopBackStack(
        val route: String,
        val inclusive: Boolean,
        val saveState: Boolean = false,
    ) : NavigatorIntent

    class Directions(
        val destination: String,
        val builder: NavOptionsBuilder.() -> Unit = {},
    ) : NavigatorIntent {
        override fun toString(): String = "destination=$destination"
    }

    data class NavigateTopLevel(val route: String) : NavigatorIntent
}