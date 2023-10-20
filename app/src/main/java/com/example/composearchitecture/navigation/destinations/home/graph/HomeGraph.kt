package com.example.composearchitecture.navigation.destinations.home.graph

import com.example.composearchitecture.navigation.base.NavigationDestination
import com.example.composearchitecture.navigation.base.NavigationGraph
import com.example.composearchitecture.navigation.destinations.home.destinations.HomeScreenBottomNavDestination
import javax.inject.Inject

class HomeGraph @Inject constructor() : NavigationGraph {
    override val startingDestination: NavigationDestination = HomeScreenBottomNavDestination
    override val route: String = "HomeGraph"
}
