package com.example.composearchitecture.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composearchitecture.navigation.base.NavigationDestination
import com.example.composearchitecture.navigation.base.NavigationGraphEntry
import com.example.composearchitecture.navigation.base.StableHolder
import com.example.composearchitecture.navigation.destinations.home.destinations.HomeScreenBottomNavDestination
import com.example.composearchitecture.navigation.destinations.home.directions.HomeScreenDirections
import javax.inject.Inject

internal class HomeScreenGraphEntry @Inject constructor(
    private val homeScreenDirections: HomeScreenDirections
) : NavigationGraphEntry {
    override val navigationDestination: NavigationDestination = HomeScreenBottomNavDestination

    @Composable
    override fun Render(controller: StableHolder<NavHostController>) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(homeScreenDirections::openLogin)
    }
}