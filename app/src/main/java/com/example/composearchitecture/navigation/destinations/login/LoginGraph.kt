package com.example.composearchitecture.navigation.destinations.login

import com.example.composearchitecture.navigation.base.NavigationGraph
import javax.inject.Inject

class LoginGraph @Inject constructor(override val startingDestination: LoginScreenDestination) :
    NavigationGraph {
    override val route: String = "LoginGraph"
}