package com.example.composearchitecture.navigation.bottom_navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composearchitecture.navigation.base.NavigationDestination

@Immutable
data class BottomNavigationEntry(
    val destination: NavigationDestination,
    val text: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
