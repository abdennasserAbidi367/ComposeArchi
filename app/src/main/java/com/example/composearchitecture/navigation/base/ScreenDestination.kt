package com.example.composearchitecture.navigation.base

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry
import com.example.composearchitecture.navigation.base.AnimatedDestination
import com.example.composearchitecture.navigation.base.NavigationDestination

@Stable
interface ScreenDestination : NavigationDestination, AnimatedDestination {

    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?
        get() = null

    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)?
        get() = null
}