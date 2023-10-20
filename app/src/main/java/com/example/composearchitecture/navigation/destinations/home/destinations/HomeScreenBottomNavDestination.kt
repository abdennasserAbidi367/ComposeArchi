package com.example.composearchitecture.navigation.destinations.home.destinations

import androidx.navigation.NamedNavArgument
import com.example.composearchitecture.navigation.base.ScreenDestination
import com.example.composearchitecture.showBottomNamedArgument

object HomeScreenBottomNavDestination : ScreenDestination {
    override fun route(): String = "HomeScreenBottomNavDestination"

    override val arguments: List<NamedNavArgument>
        get() = listOf(showBottomNamedArgument)
}
