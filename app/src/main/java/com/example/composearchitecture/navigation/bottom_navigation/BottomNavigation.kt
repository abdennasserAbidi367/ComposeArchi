package com.example.composearchitecture.navigation.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import com.example.composearchitecture.navigation.base.ImmutableHolder


@Composable
internal fun BottomNavigation(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry?,
    hideBottomNav: Boolean,
    bottomNavigationEntries: ImmutableHolder<List<BottomNavigationEntry>>,
    onTopLevelClick: (route: String) -> Unit
) {
    AnimatedVisibility(
        visible = !hideBottomNav,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = modifier.fillMaxWidth(),
    ) {
        BottomAppBar(modifier = Modifier.fillMaxWidth()) {
            bottomNavigationEntries.item.forEach { bottomEntry ->
                val isSelected = navBackStackEntry?.destination?.route == bottomEntry.destination.route()
                NavigationBarItem(
                    selected = isSelected,
                    alwaysShowLabel = true,
                    onClick = {
                        onTopLevelClick(bottomEntry.route)
                    },
                    label = {
                        Text(
                            modifier = Modifier
                                .wrapContentSize(unbounded = true),
                            softWrap = false,
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                            text = bottomEntry.text,
                        )
                    },
                    icon = {
                        Crossfade(targetState = isSelected, label = "bottom-nav-icon") { isSelectedIcon ->
                            if (isSelectedIcon) {
                                Icon(imageVector = bottomEntry.selectedIcon, contentDescription = bottomEntry.text)
                            } else {
                                Icon(imageVector = bottomEntry.unselectedIcon, contentDescription = bottomEntry.text)
                            }
                        }
                    },
                )
            }
        }
    }
}
