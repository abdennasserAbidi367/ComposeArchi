package com.example.composearchitecture.navigation.destinations.login

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavBackStackEntry
import com.example.composearchitecture.navigation.base.ScreenDestination
import javax.inject.Inject

class LoginScreenDestination @Inject constructor() : ScreenDestination {
    override fun route(): String = "LoginScreenDestination"

    override val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)
        get() = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up)
        }

    override val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)
        get() =  {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down)
        }
}