package com.example.composearchitecture

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.example.composearchitecture.navigation.base.AnimatedDestination
import com.example.composearchitecture.navigation.base.BottomSheetDestination
import com.example.composearchitecture.navigation.base.DialogDestination
import com.example.composearchitecture.navigation.base.NavigationGraph
import com.example.composearchitecture.navigation.base.NavigationGraphEntry
import com.example.composearchitecture.navigation.base.ScreenDestination
import com.example.composearchitecture.navigation.base.StableHolder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

fun NavGraphBuilder.addGraphs(
    navController: StableHolder<NavHostController>,
    navigationGraphs: Map<NavigationGraph, Set<NavigationGraphEntry>>,
    showAnimations: Boolean,
) {
    navigationGraphs.forEach { (navigatorGraph, destinationGraphEntries) ->
        navigation(startDestination = navigatorGraph.startingDestination.route(), route = navigatorGraph.route) {
            destinationGraphEntries.forEach { destinationGraphEntry ->
                addDestinationBasedOnType(destinationGraphEntry, navController, showAnimations)
            }
        }
    }
}

private fun NavGraphBuilder.addDestinationBasedOnType(
    navigationGraphEntry: NavigationGraphEntry,
    navController: StableHolder<NavHostController>,
    showAnimations: Boolean,
) {
    when (navigationGraphEntry.navigationDestination) {
        is DialogDestination -> addDialogDestinations(navController, navigationGraphEntry)
        is ScreenDestination -> addComposableDestinations(navController, navigationGraphEntry, showAnimations)
        is BottomSheetDestination -> addBottomSheetDestinations(navController, navigationGraphEntry)
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
private fun NavGraphBuilder.addBottomSheetDestinations(navController: StableHolder<NavHostController>, entry: NavigationGraphEntry) {
    val destination = entry.navigationDestination
    bottomSheet(destination.route(), destination.arguments, destination.deepLinks) {
        entry.Render(navController)
    }
}

private fun NavGraphBuilder.addDialogDestinations(navController: StableHolder<NavHostController>, entry: NavigationGraphEntry) {
    val destination = entry.navigationDestination as DialogDestination
    dialog(
        destination.route(),
        destination.arguments,
        destination.deepLinks,
        destination.dialogProperties,
    ) {
        entry.Render(navController)
    }
}

private fun NavGraphBuilder.addComposableDestinations(
    navController: StableHolder<NavHostController>,
    entry: NavigationGraphEntry,
    showAnimations: Boolean,
) {
    val destination = entry.navigationDestination
    if (destination is AnimatedDestination && showAnimations) {
        composable(
            destination.route(),
            destination.arguments,
            destination.deepLinks,
            destination.enterTransition,
            destination.exitTransition,
            destination.popEnterTransition,
            destination.popExitTransition,
        ) {
            entry.Render(navController)
        }
    } else {
        composable(
            destination.route(),
            destination.arguments,
            destination.deepLinks,
        ) {
            entry.Render(navController)
        }
    }
}
