package com.example.composearchitecture.feature.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.composearchitecture.ui.theme.Purple500

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

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
    var items by remember { mutableStateOf(listOf<String>()) }
    var i by remember { mutableStateOf(Int) }
    val username = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(topBar = {
        TabRow(selectedTabIndex = tabIndex) {
            titles.fastForEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index })
            }
        }
    }, content = { paddingValues ->
        Surface(modifier = Modifier.padding(2.dp, paddingValues.calculateTopPadding(), 2.dp, 2.dp)) {
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

            } else if (tabIndex == 1) {
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
            } else {
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (flow, textField1, clickableText2) = createRefs()
                    FlowRow(
                        modifier = Modifier.constrainAs(flow) {
                            top.linkTo(parent.top, margin = 16.dp)
                            linkTo(parent.start, parent.end, bias = 0.1f)
                            /*start.linkTo(parent.start, margin = 1.dp)
                            end.linkTo(parent.end, margin = 1.dp)*/
                        }) {
                        repeat(items.size) { index ->
                            Box(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(4.dp)
                                    .background(Color.Gray)
                                    .clickable {
                                        // Handle item click
                                        items = items - items[index]
                                    }
                            ) {
                                Text(
                                    text = items[index],
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(1.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF9681EB))
                                )
                            }
                        }
                    }

                    TextField(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.constrainAs(textField1) {
                            top.linkTo(flow.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            bottom.linkTo(parent.bottom, margin = 16.dp)
                        }.border(BorderStroke(width = 2.dp, color = Purple500), shape = RoundedCornerShape(20.dp)),
                        singleLine = true,
                        label = { Text(text = "Username") },
                        value = username.value,
                        onValueChange = { username.value = it }
                    )

                    Text(text = "Login",
                        color = Color.White,
                        modifier = Modifier
                            .constrainAs(clickableText2) {
                                top.linkTo(textField1.top, margin = 16.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                                end.linkTo(parent.end, margin = 16.dp)
                            }
                            .clickable {
                                items = items + username.value.text
                                Log.i("items", "HomeScreen: $items")
                            },
                        style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
                    )
                }


            }

        }
    })
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