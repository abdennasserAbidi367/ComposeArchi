package com.example.composearchitecture.navigation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavHostController

@Immutable
interface NavigationGraphEntry {
    val navigationDestination: NavigationDestination
    @Composable
    fun Render(controller: StableHolder<NavHostController>)
}