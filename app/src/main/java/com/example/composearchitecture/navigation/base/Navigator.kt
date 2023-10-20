package com.example.composearchitecture.navigation.base

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.flow.Flow

interface Navigator {

    val destinations: Flow<NavigatorEvent>

    fun navigateUp(): Boolean
    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true }): Boolean
/*
    val directions: Flow<NavigatorIntent>*/

    fun navigateSingleTop(
        destination: String,
        builder: NavOptionsBuilder.() -> Unit = { launchSingleTop = true },
    )

    fun navigate(
        destination: String,
    )

    fun navigateTopLevel(
        destination: String,
    )

    /*fun navigate(
        destination: String,
        builder: NavOptionsBuilder.() -> Unit,
    )*/

    fun popBackStack(
        destination: String,
        inclusive: Boolean,
        saveState: Boolean = false,
    )

    fun popCurrentBackStack()

    fun navigate(navigatorIntent: NavigatorIntent)
}