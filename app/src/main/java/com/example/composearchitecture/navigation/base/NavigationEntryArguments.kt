package com.example.composearchitecture.navigation.base

import android.os.Bundle
import androidx.compose.runtime.Immutable
import androidx.navigation.NavBackStackEntry

@Immutable
interface NavigationEntryArguments {
    val currentNavBackStackEntry: NavBackStackEntry
    val arguments: Bundle? get() = currentNavBackStackEntry.arguments
}