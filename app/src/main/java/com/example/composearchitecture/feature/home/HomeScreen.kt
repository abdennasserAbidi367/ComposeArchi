package com.example.composearchitecture.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.composearchitecture.data.DataProvider
import com.example.composearchitecture.data.Puppy
import com.example.composearchitecture.ui.theme.ComposedLibThemeSurface

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}
@Composable
internal fun HomeScreen(onClick: () -> Unit) {

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        Log.i("lifecycleExp", "lifecycleEvent: $lifecycleEvent")

        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            // initiate data reloading
            Log.i("lifecycleExp", "HomeScreen: Is resumed")
        }
    }



    val titles = listOf("Tab 1", "Tab 2", "Tab 3")
    var tabIndex by remember { mutableIntStateOf(0) }
    val puppies = remember { DataProvider.puppyList }

    Scaffold(topBar = {
        TabRow(selectedTabIndex = tabIndex) {
            titles.fastForEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index })
            }
        }
    }) { paddingValues ->

        if (tabIndex == 0) {
            AndroidView(
                factory = { ctx ->
                    RecyclerView(ctx).apply {
                        layoutManager = LinearLayoutManager(ctx)
                        adapter = HomeAdapter()
                    }
                },
                update = { recyclerView ->
                    (recyclerView.adapter as? HomeAdapter)?.submitList(puppies)
                }
            )
            /*LazyColumn(modifier = Modifier.fillMaxWidth(),  contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                items(items = puppies, itemContent = {
                    PuppyListItem(it)
                })
            }*/

        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick) {
                     Text(text = "Selected page: ${titles[tabIndex]}")
                }
            }
        }

    }
}

@Composable
fun PuppyListItem(puppy: Puppy) {
    Row {
        Column {
            Text(text = puppy.title)
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Text(text = "VIEW DETAIL")
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    ComposedLibThemeSurface {
        HomeScreen {

        }
    }
}