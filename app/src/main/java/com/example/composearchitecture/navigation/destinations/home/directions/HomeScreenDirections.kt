package com.example.composearchitecture.navigation.destinations.home.directions

import android.util.Log
import com.example.composearchitecture.navigation.base.Navigator
import com.example.composearchitecture.navigation.destinations.login.LoginScreenDestination
import javax.inject.Inject

class HomeScreenDirections @Inject constructor(
    private val navigator: Navigator,
    private val loginScreenDestination: LoginScreenDestination
) {

    fun openLogin() {
        Log.i("test", "openLogin: test")
        navigator.navigate(loginScreenDestination.route())
    }
}