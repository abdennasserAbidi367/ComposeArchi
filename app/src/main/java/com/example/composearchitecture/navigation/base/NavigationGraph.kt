package com.example.composearchitecture.navigation.base

import androidx.compose.runtime.Immutable
import com.example.composearchitecture.navigation.base.NavigationDestination

@Immutable
interface NavigationGraph {
    val startingDestination: NavigationDestination
    val route: String
}