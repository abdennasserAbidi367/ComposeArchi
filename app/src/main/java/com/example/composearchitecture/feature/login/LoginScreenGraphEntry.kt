package com.example.composearchitecture.feature.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composearchitecture.navigation.base.NavigationGraphEntry
import com.example.composearchitecture.navigation.base.Navigator
import com.example.composearchitecture.navigation.base.StableHolder
import com.example.composearchitecture.navigation.destinations.login.LoginScreenDestination
import javax.inject.Inject

internal class LoginScreenGraphEntry @Inject constructor(
    override val navigationDestination: LoginScreenDestination,
    private val navigator: Navigator
) : NavigationGraphEntry {

    @Composable
    override fun Render(controller: StableHolder<NavHostController>) {
        LoginScreen(navigator::navigateUp)
    }
}