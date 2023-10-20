package com.example.composearchitecture.navigation.base

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NavigatorImpl @Inject constructor() : Navigator, NavigatorDirections {

    private val navigationEvents = Channel<NavigatorEvent>(Channel.BUFFERED)
    override val destinations = navigationEvents.receiveAsFlow()
    private val _directions  = Channel<NavigatorIntent>(Channel.BUFFERED)
    override val directions = _directions.receiveAsFlow()

    override fun navigateSingleTop(destination: String, builder: NavOptionsBuilder.() -> Unit) {

    }

    override fun navigateTopLevel(destination: String) {
        _directions.trySend(NavigatorIntent.NavigateTopLevel(destination))
    }

    override fun popBackStack(destination: String, inclusive: Boolean, saveState: Boolean) {
        _directions.trySend(NavigatorIntent.PopBackStack(destination, inclusive, saveState))
    }

    override fun popCurrentBackStack() {
        _directions.trySend(NavigatorIntent.PopCurrentBackStack)
    }

    override fun navigateUp(): Boolean = navigationEvents.trySend(NavigatorEvent.NavigateUp).isSuccess
    override fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit): Boolean = navigationEvents.trySend(
        NavigatorEvent.Directions(route, builder)).isSuccess

    override fun navigate(destination: String) {
        _directions.trySend(NavigatorIntent.Directions(destination))
    }

    override fun navigate(navigatorIntent: NavigatorIntent) {
        _directions.trySend(navigatorIntent)
    }

}