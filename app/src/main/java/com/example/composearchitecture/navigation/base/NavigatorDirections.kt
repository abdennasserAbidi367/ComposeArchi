package com.example.composearchitecture.navigation.base

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow

@Immutable
interface NavigatorDirections {
    val directions: Flow<NavigatorIntent>
}