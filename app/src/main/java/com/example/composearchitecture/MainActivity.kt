package com.example.composearchitecture

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.composearchitecture.bottombar.TopLevelDestinationsProvider
import com.example.composearchitecture.navigation.base.ImmutableHolder
import com.example.composearchitecture.navigation.base.NavigationGraph
import com.example.composearchitecture.navigation.base.NavigationGraphEntry
import com.example.composearchitecture.navigation.base.Navigator
import com.example.composearchitecture.navigation.base.NavigatorDirections
import com.example.composearchitecture.navigation.base.NavigatorIntent
import com.example.composearchitecture.navigation.base.asImmutable
import com.example.composearchitecture.navigation.base.asStable
import com.example.composearchitecture.navigation.bottom_navigation.BottomNavigation
import com.example.composearchitecture.navigation.bottom_navigation.BottomNavigationEntry
import com.example.composearchitecture.navigation.graphs.GraphFactory
import com.example.composearchitecture.ui.theme.ComposedLibThemeSurface
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var navigatorDirections: NavigatorDirections
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var graphFactory: GraphFactory
    @Inject lateinit var topLevelDestinationsProvider: TopLevelDestinationsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposedLibThemeSurface {
                AppScaffold(
                    startingDestination = topLevelDestinationsProvider.getStartingDestination(),
                    graphs = { graphFactory.graphsWithDestinations },
                    showAnimations = true,
                    navigatorDirections = navigatorDirections,
                    bottomNavigationEntries = topLevelDestinationsProvider.bottomNavigationEntries.asImmutable,
                    navigator = navigator
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun AppScaffold(
    startingDestination: String,
    graphs: () -> Map<NavigationGraph, Set<NavigationGraphEntry>>,
    showAnimations: Boolean,
    navigatorDirections: NavigatorDirections,
    bottomNavigationEntries: ImmutableHolder<List<BottomNavigationEntry>>,
    navigator: Navigator
) {
    val bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator()
    val navController: NavHostController = rememberNavController(bottomSheetNavigator)

    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)
    val hideBottomNav: Boolean by remember {
        derivedStateOf { navBackStackEntry.hideBottomNavigation }
    }
    val currentRoute by remember {
        derivedStateOf { navBackStackEntry?.destination?.route }
    }
    LaunchedEffect(currentRoute) {
        Log.d(
            "Current destination", "${navBackStackEntry?.destination} with arguments ${navBackStackEntry?.arguments}",
        )
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = MaterialTheme.shapes.large.copy(topStart = CornerSize(16.dp), topEnd = CornerSize(16.dp)),
        sheetElevation = 0.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = startingDestination,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() },
                ) {
                    addGraphs(navController.asStable, graphs(), showAnimations)
                }

                BottomNavigation(
                    bottomNavigationEntries = bottomNavigationEntries,
                    modifier = Modifier.align(Alignment.BottomStart),
                    hideBottomNav = hideBottomNav,
                    navBackStackEntry = navBackStackEntry,
                    onTopLevelClick = navigator::navigateTopLevel
                )
            }
        }
    }
    NavHostControllerEvents(
        navigator = navigatorDirections,
        navController = navController,
    )
}

@Composable
private fun NavHostControllerEvents(
    navigator: NavigatorDirections,
    navController: NavHostController,
) {
    LaunchedEffect(navController) {
        navigator.directions.collectLatest { navigatorEvent ->
            when (navigatorEvent) {
                is NavigatorIntent.NavigateUp -> navController.navigateUp()
                is NavigatorIntent.Directions -> navController.navigate(navigatorEvent.destination, navigatorEvent.builder)
                NavigatorIntent.PopCurrentBackStack -> navController.popBackStack()
                is NavigatorIntent.PopBackStack -> navController.popBackStack(
                    navigatorEvent.route,
                    navigatorEvent.inclusive,
                    navigatorEvent.saveState,
                )

                is NavigatorIntent.NavigateTopLevel -> {
                    val topLevelNavOptions = navOptions {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                    navController.navigate(navigatorEvent.route, topLevelNavOptions)
                }
            }
            Log.d("NavDirections", navigatorEvent.toString())
        }
    }
}