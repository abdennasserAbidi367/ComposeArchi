package com.example.composearchitecture.navigation.destinations.home.directions

import com.example.composearchitecture.navigation.base.Navigator
import com.example.composearchitecture.navigation.destinations.login.LoginScreenDestination
import javax.inject.Inject

class HomeScreenDirections @Inject constructor(
    private val navigator: Navigator,
    private val loginScreenDestination: LoginScreenDestination
) {

    fun openLogin() = navigator.navigateSingleTop(loginScreenDestination.route())
}